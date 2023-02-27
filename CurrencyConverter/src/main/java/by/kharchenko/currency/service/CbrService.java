package by.kharchenko.currency.service;

import by.kharchenko.currency.client.HttpCurrencyDateRateClient;
import by.kharchenko.currency.schema.ValCurs;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static java.util.stream.Collectors.toMap;

@Service
public class CbrService {

    private final Cache<LocalDate, Map<String, Double>> cache;
    private final HttpCurrencyDateRateClient dateRateClient;

    public CbrService(HttpCurrencyDateRateClient dateRateClient) {
        this.dateRateClient = dateRateClient;
        this.cache = CacheBuilder.newBuilder().build();
    }

    public double requestMyCurrencyCode(String code) {
        try {
            return cache.get(LocalDate.now(), this::callAllByCurrentDate).get(code);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, Double> callAllByCurrentDate() {
        String xml = dateRateClient.requestByDate(LocalDate.now());
        ValCurs valCurs = unmarshall(xml);
        return valCurs.getValute().stream().collect(toMap(ValCurs.Valute::getCharCode, item -> parseWithLocale(item.getValue())));
    }

    private double parseWithLocale(String currency) {
        try {
            double v = NumberFormat.getNumberInstance(Locale.FRANCE).parse(currency).doubleValue();
            return v;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private ValCurs unmarshall(String xml) {
        try (StringReader reader = new StringReader(xml)) {
            try {
                JAXBContext context = JAXBContext.newInstance(ValCurs.class);
                return (ValCurs) context.createUnmarshaller().unmarshal(reader);
            } catch (JAXBException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
