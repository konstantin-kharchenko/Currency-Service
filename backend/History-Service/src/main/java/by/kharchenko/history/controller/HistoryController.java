package by.kharchenko.history.controller;

import by.kharchenko.history.dto.HistoryDto;
import by.kharchenko.history.service.HistoryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/history")
@AllArgsConstructor
public class HistoryController {

    private final HistoryService historyService;

    @GetMapping("/find")
    public Page<HistoryDto> findByAccountNumber(@RequestParam(value = "account-number", required = false) String accountNumber, Pageable pageable){
        return historyService.findByAccountNumber(accountNumber, pageable);
    }

}
