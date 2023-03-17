package by.kharchenko.currency.controller;

import by.kharchenko.currency.dto.CurrenciesDto;
import by.kharchenko.currency.service.CbrService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/currency")
@RequiredArgsConstructor
public class CurrencyController {

    private final CbrService cbrService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @GetMapping("/rate/{code}")
    public double currencyRate(@PathVariable("code") String code) {
        double currency = cbrService.requestMyCurrencyCode(code);
        return currency;
    }

    @Scheduled(cron = "0 0 0 * * *")
    @Async
    public void sendMainCurrencies(){
        Double usd = cbrService.requestMyCurrencyCode("USD");
        Double eur = cbrService.requestMyCurrencyCode("USD");
        Double gbp = cbrService.requestMyCurrencyCode("GBP");
        simpMessagingTemplate.convertAndSend(
                "/currency/main-currency/outgoing",
                new CurrenciesDto(usd, eur, gbp)
        );
    }
}
