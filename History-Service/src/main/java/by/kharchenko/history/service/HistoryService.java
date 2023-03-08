package by.kharchenko.history.service;

import by.kharchenko.history.dto.HistoryDto;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface HistoryService {
    void saveFromKafka(ConsumerRecord<Long, String> record);
    List<HistoryDto> findAllByUserId(Long userId);
    Page<HistoryDto> findByAccountNumber(String accountNumber, Pageable pageable);
}
