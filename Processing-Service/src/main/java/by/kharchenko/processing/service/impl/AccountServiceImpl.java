package by.kharchenko.processing.service.impl;

import by.kharchenko.processing.dto.AccountDto;
import by.kharchenko.processing.dto.CreateAccountDto;
import by.kharchenko.processing.dto.TransferAccountDto;
import by.kharchenko.processing.entity.Account;
import by.kharchenko.processing.entity.AccountEvent;
import by.kharchenko.processing.entity.User;
import by.kharchenko.processing.mapper.AccountMapper;
import by.kharchenko.processing.repository.AccountRepository;
import by.kharchenko.processing.repository.UserRepository;
import by.kharchenko.processing.security.JwtAuthentication;
import by.kharchenko.processing.service.AccountService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final String currencyUrl;

    public AccountServiceImpl(AccountRepository accountRepository, UserRepository userRepository, ApplicationEventPublisher eventPublisher, @Value("${currency-service.uri}") String currencyUrl) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.eventPublisher = eventPublisher;
        this.currencyUrl = currencyUrl;
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
    public List<AccountDto> findAll() {
        Long id = ((JwtAuthentication) SecurityContextHolder.getContext().getAuthentication()).getUserId();
        List<Account> accounts = accountRepository.findByUserId(id);
        return AccountMapper.INSTANCE.accountListToAccountDtoList(accounts);
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
    public void transfer(TransferAccountDto transferAccountDto) throws Exception {
        Account fromAccount = accountRepository.findByAccountNumber(transferAccountDto.getFromAccount()).orElseThrow(() -> new Exception("This account number not exists"));
        Account toAccount = accountRepository.findByAccountNumber(transferAccountDto.getToAccount()).orElseThrow(() -> new Exception("This account number not exists"));

        WebClient fromWebClient = WebClient.create(currencyUrl);
        WebClient toWebClient = WebClient.create(currencyUrl);

        BigDecimal fromCurrency = fromWebClient
                .get()
                .uri(fromAccount.getCurrency())
                .retrieve()
                .bodyToMono(BigDecimal.class)
                .block();

        BigDecimal toCurrency = toWebClient
                .get()
                .uri(toAccount.getCurrency())
                .retrieve()
                .bodyToMono(BigDecimal.class)
                .block();

        BigDecimal newMoney = transferAccountDto.getCount().multiply(fromCurrency);
        newMoney = newMoney.divide(toCurrency, new MathContext(5));

        fromAccount.setMoneyCount(fromAccount.getMoneyCount().subtract(transferAccountDto.getCount()));
        toAccount.setMoneyCount(toAccount.getMoneyCount().add(newMoney));

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        eventPublisher.publishEvent(createEvent(
                UUID.randomUUID().toString()
                , fromAccount.getUser().getId()
                , toAccount.getId()
                , toAccount.getCurrency()
                , transferAccountDto.getCount()
                , new Date()
        ));
    }

    @Override
    public void delete(Long id) {
        accountRepository.deleteById(id);
    }

    @Override
    public void deleteByAccountNumber(String accountNumber) {
        accountRepository.deleteByAccountNumber(accountNumber);
    }

    private AccountEvent createEvent(String uuid,Long userId,Long accountId,String currency,BigDecimal amount,Date created){
        return AccountEvent
                .builder()
                .uuid(uuid)
                .accountId(accountId)
                .userId(userId)
                .currency(currency)
                .amount(amount)
                .created(created)
                .build();
    }

}
