package by.kharchenko.processing.service;

import by.kharchenko.processing.dto.AccountEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;


@Service
public class AccountEventSendingService {

    private final KafkaTemplate<Long, String> kafkaTemplate;
    private final ObjectMapper mapper = new ObjectMapper();

    public AccountEventSendingService(KafkaTemplate<Long, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(AccountEvent event) throws JsonProcessingException {
        Long accountId = event.getToAccountId();
        String message = mapper.writeValueAsString(event);

        var future = kafkaTemplate.send("History",accountId, message);
        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
