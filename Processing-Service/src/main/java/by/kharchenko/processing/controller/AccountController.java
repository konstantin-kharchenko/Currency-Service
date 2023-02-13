package by.kharchenko.processing.controller;

import by.kharchenko.processing.dto.AccountDto;
import by.kharchenko.processing.dto.AccountNumberDto;
import by.kharchenko.processing.dto.CreateAccountDto;
import by.kharchenko.processing.dto.TransferAccountDto;
import by.kharchenko.processing.entity.Account;
import by.kharchenko.processing.entity.AccountEvent;
import by.kharchenko.processing.service.AccountService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/processing")
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/create")
    public void create(@RequestBody CreateAccountDto createAccountDto){
        accountService.add(createAccountDto);
    }

    @GetMapping("/delete/{id}")
    public void deleteById(@PathVariable("id") Long id) throws Exception {
        accountService.delete(id);
    }

    @PostMapping("/transfer")
    public void transfer(@RequestBody TransferAccountDto transferAccountDto) throws Exception {
        accountService.transfer(transferAccountDto);
    }

    @GetMapping("/find-all")
    public List<AccountDto> findAll() throws Exception {
        return accountService.findAll();
    }

    @GetMapping("/find/{id}")
    public AccountDto findById(@PathVariable("id") Long id) throws Exception {
        return accountService.find(id);
    }

    @GetMapping("/find")
    public AccountDto findByAccountNumber(@RequestBody AccountNumberDto accountNumberDto) throws Exception {
        return accountService.findByAccountNumber(accountNumberDto.getAccountNumber());
    }

    @GetMapping("/delete")
    public void deleteByAccountNumber(@RequestBody AccountNumberDto accountNumberDto) throws Exception {
        accountService.deleteByAccountNumber(accountNumberDto.getAccountNumber());
    }
}
