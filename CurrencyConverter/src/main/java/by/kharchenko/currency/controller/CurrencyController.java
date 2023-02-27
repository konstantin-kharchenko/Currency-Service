package by.kharchenko.currency.controller;

import by.kharchenko.currency.service.CbrService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/currency")
@RequiredArgsConstructor
public class CurrencyController {

    private final CbrService cbrService;

    @GetMapping("/rate/{code}")
    public double currencyRate(@PathVariable("code") String code) {
        double currency = cbrService.requestMyCurrencyCode(code);
        return currency;
    }
}
