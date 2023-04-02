package by.kharchenko.currency.service;

import by.kharchenko.currency.client.HttpCurrencyDateRateClient;
import by.kharchenko.currency.dto.CurrencyDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class NbrbService {

    private final Cache<LocalDate, Map<String, BigDecimal>> cache;
    private final HttpCurrencyDateRateClient dateRateClient;

    public NbrbService(HttpCurrencyDateRateClient dateRateClient) {
        this.dateRateClient = dateRateClient;
        this.cache = CacheBuilder.newBuilder().build();
    }

    public BigDecimal requestMyCurrencyCode(String code) {
        try {
            return cache.get(LocalDate.now(), this::callAllByCurrentDate).get(code);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, BigDecimal> callAllByCurrentDate() throws JsonProcessingException {
        List<CurrencyDto> data = dateRateClient.requestByDate();
        return convertListToMap(data);
    }

    private Map<String, BigDecimal> convertListToMap(List<CurrencyDto> data) {
        Map<String, BigDecimal> map = new HashMap<>();
        for (CurrencyDto currency: data) {
            map.put(currency.getCurAbbreviation(), currency.getCurOfficialRate());
        }
        return map;
    }
}
