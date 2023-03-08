package by.kharchenko.processing.service.impl;

import by.kharchenko.processing.dto.AccountDto;
import by.kharchenko.processing.dto.AddCountDto;
import by.kharchenko.processing.dto.CreateAccountDto;
import by.kharchenko.processing.dto.TransferAccountDto;
import by.kharchenko.processing.entity.Account;
import by.kharchenko.processing.dto.AccountEvent;
import by.kharchenko.processing.entity.ActionType;
import by.kharchenko.processing.entity.User;
import by.kharchenko.processing.exception.AccountNumberNotExistsException;
import by.kharchenko.processing.exception.MoreAmountException;
import by.kharchenko.processing.exception.TransactionalException;
import by.kharchenko.processing.mapper.AccountMapper;
import by.kharchenko.processing.repository.AccountRepository;
import by.kharchenko.processing.repository.HistoryRepository;
import by.kharchenko.processing.repository.UserRepository;
import by.kharchenko.processing.security.JwtAuthentication;
import by.kharchenko.processing.service.AccountService;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final HistoryRepository historyRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final String currencyUrl;
    private final Cache<String, BigDecimal> currencyCache;
    private final List<String> mainCurrencies = new ArrayList<>(List.of("USD", "EUR", "GBP"));
    private final AccountMapper accountMapper;

    public AccountServiceImpl(AccountRepository accountRepository, UserRepository userRepository, HistoryRepository historyRepository, ApplicationEventPublisher eventPublisher, @Value("${currency-service.uri}") String currencyUrl,
                              AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.historyRepository = historyRepository;
        this.eventPublisher = eventPublisher;
        this.currencyUrl = currencyUrl;
        this.currencyCache = CacheBuilder.newBuilder().build();
        this.accountMapper = accountMapper;
    }

    @Override
    public void add(CreateAccountDto createAccountDto) {

        Long id = ((JwtAuthentication) SecurityContextHolder.getContext().getAuthentication()).getUserId();

        Account account = AccountMapper.INSTANCE.createAccountDtoToUser(createAccountDto);
        User user = userRepository.findById(id).get();
        account.setUser(user);
        account.setMoneyCount(new BigDecimal(0));
        account.setAccountNumber(UUID.randomUUID().toString());
        accountRepository.save(account);
    }

    @Override
    @Transactional
    public AccountDto addMoneyCount(AddCountDto addCountDto) throws AccountNumberNotExistsException, TransactionalException {
        Account account = accountRepository.findByAccountNumber(addCountDto.getAccountNumber()).orElseThrow(() -> new AccountNumberNotExistsException("This account number not exists"));
        System.out.println(account);
        BigDecimal nowCount = account.getMoneyCount();
        BigDecimal resultCount = nowCount.add(addCountDto.getMoneyCount());
        account.setMoneyCount(resultCount);

        try {
            Account savedAccount = accountRepository.save(account);

            eventPublisher.publishEvent(createEvent(account.getUser().getId()
                    , -1L
                    , account.getId()
                    , ""
                    , account.getCurrency()
                    , addCountDto.getMoneyCount()
                    , addCountDto.getMoneyCount()
                    , new Date()
                    , ActionType.REPLENISHMENT
            ));

            return AccountMapper.INSTANCE.accountToAccountDto(savedAccount);
        } catch (Exception e) {
            throw new TransactionalException(e);
        }
    }

    @Override
    public Page<AccountDto> findAll(Pageable pageable, String currency) {
        Long id = ((JwtAuthentication) SecurityContextHolder.getContext().getAuthentication()).getUserId();
        Page<Account> accountPage = null;
        if (currency != null){
            accountPage = accountRepository.findByUserIdAndCurrency(id, currency, pageable);
        }else {
            accountPage = accountRepository.findByUserId(id, pageable);
        }
        return accountPage.map(accountMapper::accountToAccountDto);
    }

    @Override
    public AccountDto find(Long id) {
        return AccountMapper.INSTANCE.accountToAccountDto(accountRepository.findById(id).get());
    }

    @Override
    public AccountDto findByAccountNumber(String accountNumber) throws Exception {
        Account account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new Exception("This account number not exists"));
        return AccountMapper.INSTANCE.accountToAccountDto(account);
    }

    @Override
    @Transactional
    public AccountDto transfer(TransferAccountDto transferAccountDto) throws TransactionalException, MoreAmountException, AccountNumberNotExistsException {
        try {

            Account fromAccount = accountRepository.findByAccountNumber(transferAccountDto.getFromAccount()).orElseThrow(() -> new AccountNumberNotExistsException("This account number not exists"));
            Account toAccount = accountRepository.findByAccountNumber(transferAccountDto.getToAccount()).orElseThrow(() -> new AccountNumberNotExistsException("This account number not exists"));

            BigDecimal fromCurrency = currencyCache.getIfPresent(fromAccount.getCurrency());

            BigDecimal toCurrency = currencyCache.getIfPresent(toAccount.getCurrency());
            if (fromCurrency == null || toCurrency == null) {
                updateMainCurrencies();
                fromCurrency = currencyCache.getIfPresent(fromAccount.getCurrency());
                toCurrency = currencyCache.getIfPresent(toAccount.getCurrency());
            }

            BigDecimal newMoney = transferAccountDto.getCount().multiply(fromCurrency);
            newMoney = newMoney.divide(toCurrency, new MathContext(5));

            BigDecimal resultFromAccount = fromAccount.getMoneyCount().subtract(transferAccountDto.getCount());
            if (resultFromAccount.compareTo(BigDecimal.ZERO) < 0) {
                throw new MoreAmountException("you are trying to write off more than you have on your account");
            }
            fromAccount.setMoneyCount(resultFromAccount);
            toAccount.setMoneyCount(toAccount.getMoneyCount().add(newMoney));

            Account savedAccount = accountRepository.save(fromAccount);
            accountRepository.save(toAccount);

            eventPublisher.publishEvent(createEvent(fromAccount.getUser().getId()
                    , fromAccount.getId()
                    , toAccount.getId()
                    , fromAccount.getCurrency()
                    , toAccount.getCurrency()
                    , transferAccountDto.getCount()
                    , newMoney
                    , new Date()
                    , ActionType.TRANSFER
            ));

            return AccountMapper.INSTANCE.accountToAccountDto(savedAccount);
        } catch (RuntimeException e) {
            throw new TransactionalException(e);
        }
    }

    @Override
    public void delete(Long id) {
        accountRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteByAccountNumber(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber).get();
        historyRepository.deleteByAccount(account);
        accountRepository.delete(account);
    }

    private AccountEvent createEvent(Long userId
            , Long fromAccountId
            , Long toAccountId
            , String fromCurrency
            , String toCurrency
            , BigDecimal fromAmount
            , BigDecimal toAmount
            , Date created
            , ActionType action) {
        return AccountEvent
                .builder()
                .fromAccountId(fromAccountId)
                .toAccountId(toAccountId)
                .userId(userId)
                .fromCurrency(fromCurrency)
                .toCurrency(toCurrency)
                .fromAmount(fromAmount)
                .toAmount(toAmount)
                .created(created)
                .action(action)
                .build();
    }

    @Scheduled(initialDelay = 30000, fixedRate = 86400000)
    @Async
    public void updateMainCurrencies() {
        currencyCache.invalidateAll();
        for (String currency : mainCurrencies) {
            WebClient webClient = WebClient.create(currencyUrl);
            BigDecimal bigDecimalCurrency = webClient
                    .get()
                    .uri(currency)
                    .retrieve()
                    .bodyToMono(BigDecimal.class)
                    .block();

            currencyCache.put(currency, bigDecimalCurrency);
        }
    }

}
