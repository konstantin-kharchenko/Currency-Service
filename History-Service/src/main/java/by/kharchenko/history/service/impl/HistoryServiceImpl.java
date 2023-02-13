package by.kharchenko.history.service.impl;

import by.kharchenko.history.dto.AccountEvent;
import by.kharchenko.history.dto.HistoryDto;
import by.kharchenko.history.entity.Account;
import by.kharchenko.history.entity.History;
import by.kharchenko.history.entity.User;
import by.kharchenko.history.repository.AccountRepository;
import by.kharchenko.history.repository.HistoryRepository;
import by.kharchenko.history.repository.UserRepository;
import by.kharchenko.history.service.HistoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class HistoryServiceImpl implements HistoryService {

    private final HistoryRepository historyRepository;
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    @KafkaListener(topics = "History")
    public void saveFromKafka(ConsumerRecord<Long, String> record) {
        Long key = record.key();
        String value = record.value();
        System.out.println("VALUE: " + value);
        try {
            AccountEvent accountEvent = mapper.readValue(value, AccountEvent.class);

            User user = userRepository.findById(accountEvent.getUserId()).get();
            Account account = accountRepository.findById(accountEvent.getAccountId()).get();

            History history = History.builder()
                    .amount(accountEvent.getAmount())
                    .uuid(accountEvent.getUuid())
                    .currency(accountEvent.getCurrency())
                    .created(accountEvent.getCreated())
                    .user(user)
                    .account(account)
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
    public HistoryDto findByUUID(String uuid) {
        return null;
    }
}
