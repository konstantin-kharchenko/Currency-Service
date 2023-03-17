package by.kharchenko.processing.service;

import by.kharchenko.processing.dto.AccountDto;
import by.kharchenko.processing.dto.AddCountDto;
import by.kharchenko.processing.dto.CreateAccountDto;
import by.kharchenko.processing.dto.TransferAccountDto;
import by.kharchenko.processing.exception.AccountNumberNotExistsException;
import by.kharchenko.processing.exception.MoreAmountException;
import by.kharchenko.processing.exception.TransactionalException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AccountService {
    void add(CreateAccountDto createAccountDto);

    AccountDto addMoneyCount(AddCountDto addCountDto) throws AccountNumberNotExistsException, TransactionalException;

    Page<AccountDto> findAll(Pageable pageable, String currency);

    AccountDto find(Long id);

    AccountDto findByAccountNumber(String accountNumber) throws Exception;

    AccountDto transfer(TransferAccountDto transferAccountDto) throws TransactionalException, MoreAmountException, AccountNumberNotExistsException;

    void delete(Long id);

    void deleteByAccountNumber(String accountNumber);
}
