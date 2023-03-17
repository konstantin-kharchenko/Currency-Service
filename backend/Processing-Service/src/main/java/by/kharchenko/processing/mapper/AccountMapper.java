package by.kharchenko.processing.mapper;

import by.kharchenko.processing.dto.AccountDto;
import by.kharchenko.processing.dto.CreateAccountDto;
import by.kharchenko.processing.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    Account createAccountDtoToUser(CreateAccountDto createAccountDto);

    AccountDto accountToAccountDto(Account account);

    List<AccountDto> accountListToAccountDtoList(List<Account> accounts);

}
