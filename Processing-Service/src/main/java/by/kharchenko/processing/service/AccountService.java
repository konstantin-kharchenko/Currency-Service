package by.kharchenko.processing.service;

import by.kharchenko.processing.dto.*;
import by.kharchenko.processing.entity.Account;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {
    void add(CreateAccountDto createAccountDto);

    AccountDto addMoneyCount(AddCountDto addCountDto) throws Exception;

    List<AccountDto> findAll();

    AccountDto find(Long id);

    AccountDto findByAccountNumber(String accountNumber) throws Exception;

    AccountDto transfer(TransferAccountDto transferAccountDto) throws Exception;

    void delete(Long id);

    void deleteByAccountNumber(String accountNumber);
}
