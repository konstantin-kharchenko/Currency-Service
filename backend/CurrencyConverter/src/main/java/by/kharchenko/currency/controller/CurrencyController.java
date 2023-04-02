package by.kharchenko.currency.controller;

import by.kharchenko.currency.dto.CurrenciesDto;
import by.kharchenko.currency.service.NbrbService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;

@RestController
@RequestMapping("/currency")
@RequiredArgsConstructor
public class CurrencyController {

    private final NbrbService nbrbService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @GetMapping("/rate/{code}")
    public BigDecimal currencyRate(@PathVariable("code") String code) {
        return nbrbService.requestMyCurrencyCode(code);
    }

    @Scheduled(cron = "0 * * * * *")
    @Async
    public void sendMainCurrencies(){
        System.out.println("CALL sendMainCurrencies() in time: " + new Date());
        BigDecimal usd = nbrbService.requestMyCurrencyCode("USD");
        BigDecimal eur = nbrbService.requestMyCurrencyCode("EUR");
        BigDecimal gbp = nbrbService.requestMyCurrencyCode("GBP");
        simpMessagingTemplate.convertAndSend(
                "/currency/main-currency/outgoing",
                new CurrenciesDto(usd, eur, gbp)
        );
    }
}
