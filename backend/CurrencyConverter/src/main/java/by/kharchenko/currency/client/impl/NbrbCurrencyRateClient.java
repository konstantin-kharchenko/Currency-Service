package by.kharchenko.currency.client.impl;

import by.kharchenko.currency.client.HttpCurrencyDateRateClient;
import by.kharchenko.currency.dto.CurrencyDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class NbrbCurrencyRateClient implements HttpCurrencyDateRateClient {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    public List<CurrencyDto> requestByDate() throws JsonProcessingException {
        String baseUrl = "https://www.nbrb.by/api/exrates/rates?periodicity=0";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<CurrencyDto>> response = restTemplate.exchange(baseUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<CurrencyDto>>() {});
        return response.getBody();
    }
}
