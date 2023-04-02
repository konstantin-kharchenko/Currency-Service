package by.kharchenko.currency.client;

import by.kharchenko.currency.dto.CurrencyDto;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface HttpCurrencyDateRateClient {
    List<CurrencyDto> requestByDate() throws JsonProcessingException;
}
