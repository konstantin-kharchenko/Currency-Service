package by.kharchenko.history.service;

import by.kharchenko.history.dto.HistoryDto;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.List;

public interface HistoryService {
    void saveFromKafka(ConsumerRecord<Long, String> record);
    List<HistoryDto> findAllByUserId(Long userId);
    HistoryDto findByUUID(String uuid);
}
