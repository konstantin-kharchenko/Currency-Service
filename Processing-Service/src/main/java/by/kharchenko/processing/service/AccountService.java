package by.kharchenko.processing.service;

import by.kharchenko.processing.dto.*;
import by.kharchenko.processing.entity.Account;
import by.kharchenko.processing.exception.AccountNumberNotExistsException;
import by.kharchenko.processing.exception.MoreAmountException;
import by.kharchenko.processing.exception.TransactionalException;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {
    void add(CreateAccountDto createAccountDto);

    AccountDto addMoneyCount(AddCountDto addCountDto) throws AccountNumberNotExistsException, TransactionalException;

    List<AccountDto> findAll();

    AccountDto find(Long id);

    AccountDto findByAccountNumber(String accountNumber) throws Exception;

    AccountDto transfer(TransferAccountDto transferAccountDto) throws TransactionalException, MoreAmountException, AccountNumberNotExistsException;

    void delete(Long id);

    void deleteByAccountNumber(String accountNumber);
}
