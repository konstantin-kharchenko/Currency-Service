package by.kharchenko.processing.service;

import by.kharchenko.processing.entity.AccountEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
public class AccountOperationEventListener {

    private final AccountEventSendingService sendingService;

    public AccountOperationEventListener(AccountEventSendingService sendingService) {
        this.sendingService = sendingService;
    }

    @TransactionalEventListener
    public void handleEvent(AccountEvent event) throws JsonProcessingException {
        sendingService.sendEvent(event);
    }
}
