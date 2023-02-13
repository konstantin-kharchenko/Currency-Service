package by.kharchenko.processing.service;

import by.kharchenko.processing.dto.AccountDto;
import by.kharchenko.processing.dto.CreateAccountDto;
import by.kharchenko.processing.dto.TransferAccountDto;
import by.kharchenko.processing.dto.UpdateAccountDto;
import by.kharchenko.processing.entity.Account;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {
    void add(CreateAccountDto createAccountDto);

    List<AccountDto> findAll();

    AccountDto find(Long id);

    AccountDto findByAccountNumber(String accountNumber) throws Exception;

    void transfer(TransferAccountDto transferAccountDto) throws Exception;

    void delete(Long id);

    void deleteByAccountNumber(String accountNumber);
}
