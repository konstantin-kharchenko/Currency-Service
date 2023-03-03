package by.kharchenko.processing.controller;

import by.kharchenko.processing.dto.*;
import by.kharchenko.processing.exception.AccountNumberNotExistsException;
import by.kharchenko.processing.exception.MoreAmountException;
import by.kharchenko.processing.exception.TransactionalException;
import by.kharchenko.processing.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/processing")
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/create")
    public void create(@RequestBody CreateAccountDto createAccountDto) {
        accountService.add(createAccountDto);
    }

    @PostMapping("/add-money")
    public ResponseEntity<AccountDto> addMoney(@Valid @RequestBody AddCountDto addCountDto) throws AccountNumberNotExistsException, TransactionalException {

        AccountDto accountDto = accountService.addMoneyCount(addCountDto);
        return ResponseEntity.ok(accountDto);

    }

    @PostMapping("/delete/{id}")
    public void deleteById(@PathVariable("id") Long id) throws Exception {
        accountService.delete(id);
    }

    @PostMapping("/transfer")
    public ResponseEntity<AccountDto> transfer(@Valid @RequestBody TransferAccountDto transferAccountDto) throws TransactionalException, MoreAmountException, AccountNumberNotExistsException {
        AccountDto accountDto = accountService.transfer(transferAccountDto);
        return ResponseEntity.ok(accountDto);
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

    @PostMapping("/delete")
    public void deleteByAccountNumber(@RequestBody AccountNumberDto accountNumberDto) throws Exception {
        accountService.deleteByAccountNumber(accountNumberDto.getAccountNumber());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AccountNumberNotExistsException.class)
    public ResponseEntity<ExceptionMessageDto> handleAccountNumberNotExistsException(AccountNumberNotExistsException ex) {
        return ResponseEntity.badRequest().body(new ExceptionMessageDto(1L, ex.getMessage()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MoreAmountException.class)
    public ResponseEntity<ExceptionMessageDto> handleMoreAmountException(MoreAmountException ex) {
        return ResponseEntity.badRequest().body(new ExceptionMessageDto(2L, ex.getMessage()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TransactionalException.class)
    public ResponseEntity<ExceptionMessageDto> handleTransactionalException(TransactionalException ex) {
        return ResponseEntity.badRequest().body(new ExceptionMessageDto(3L, ex.getMessage()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
