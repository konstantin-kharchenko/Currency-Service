package by.kharchenko.history.service.impl;

import by.kharchenko.history.dto.AccountEvent;
import by.kharchenko.history.dto.HistoryDto;
import by.kharchenko.history.entity.Account;
import by.kharchenko.history.entity.Action;
import by.kharchenko.history.entity.History;
import by.kharchenko.history.entity.User;
import by.kharchenko.history.mapper.HistoryMapper;
import by.kharchenko.history.repository.AccountRepository;
import by.kharchenko.history.repository.ActionRepository;
import by.kharchenko.history.repository.HistoryRepository;
import by.kharchenko.history.repository.UserRepository;
import by.kharchenko.history.service.HistoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class HistoryServiceImpl implements HistoryService {

    private final HistoryRepository historyRepository;
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final ActionRepository actionRepository;
    private final ObjectMapper mapper = new ObjectMapper();
    private final HistoryMapper historyMapper;

    @Override
    @KafkaListener(topics = "History")
    public void saveFromKafka(ConsumerRecord<Long, String> record) {
        Long key = record.key();
        String value = record.value();
        System.out.println("VALUE: " + value);
        try {
            AccountEvent accountEvent = mapper.readValue(value, AccountEvent.class);

            User user = userRepository.findById(accountEvent.getUserId()).get();
            String currency = null;
            Account fromAccount = null;
            if (!accountEvent.getFromCurrency().equals("")) {
                currency = accountEvent.getFromCurrency();
            }
            if (accountEvent.getFromAccountId() != -1L) {
                fromAccount = accountRepository.findById(accountEvent.getFromAccountId()).get();
            }
            Account toAccount = accountRepository.findById(accountEvent.getToAccountId()).get();
            Action action = actionRepository.findActionByActionType(accountEvent.getAction()).get();
            History history = History.builder()
                    .fromAmount(accountEvent.getFromAmount())
                    .toAmount(accountEvent.getToAmount())
                    .fromCurrency(currency)
                    .toCurrency(accountEvent.getToCurrency())
                    .created(accountEvent.getCreated())
                    .user(user)
                    .fromAccount(fromAccount)
                    .toAccount(toAccount)
                    .action(action)
                    .build();

            historyRepository.save(history);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<HistoryDto> findAllByUserId(Long userId) {
        return null;
    }

    @Override
    public Page<HistoryDto> findByAccountNumber(String accountNumber, Pageable pageable) {
        Account account = accountRepository.findByAccountNumber(accountNumber).get();
        Page<History> histories = historyRepository.findByAccount(account, pageable);

        Page<HistoryDto> historyDtoList = histories.map(historyMapper::HistoryToHistoryDto);
        for (int i = 0; i < historyDtoList.getContent().size(); i++) {
            historyDtoList.getContent().get(i).setActionType(histories.getContent().get(i).getAction().getActionType().toString());
            if (histories.getContent().get(i).getFromAccount() != null) {
                historyDtoList.getContent().get(i).setFromAccountNumber(histories.getContent().get(i).getFromAccount().getAccountNumber());
            }
            if (histories.getContent().get(i).getToAccount() != null) {
                historyDtoList.getContent().get(i).setToAccountNumber(histories.getContent().get(i).getToAccount().getAccountNumber());
            }
        }

        return historyDtoList;
    }
}
