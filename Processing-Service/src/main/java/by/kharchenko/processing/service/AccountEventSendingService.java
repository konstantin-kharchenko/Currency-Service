package by.kharchenko.processing.service;

import by.kharchenko.processing.entity.AccountEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.util.concurrent.ListenableFuture;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
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
        Long accountId = event.getAccountId();
        String message = mapper.writeValueAsString(event);

        var future = kafkaTemplate.send("History",accountId, message);
        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
