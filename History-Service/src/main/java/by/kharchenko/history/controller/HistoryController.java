package by.kharchenko.history.controller;

import by.kharchenko.history.dto.HistoryDto;
import by.kharchenko.history.service.HistoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/history")
@AllArgsConstructor
public class HistoryController {

    private final HistoryService historyService;

    @GetMapping("/find/{accountNumber}")
    public List<HistoryDto> findByAccountNumber(@PathVariable String accountNumber){
        return historyService.findByAccountNumber(accountNumber);
    }

}
