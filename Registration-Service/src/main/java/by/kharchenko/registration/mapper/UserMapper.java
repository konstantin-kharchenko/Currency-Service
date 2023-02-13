package by.kharchenko.registration.mapper;

import by.kharchenko.registration.dto.RegisterUserDto;
import by.kharchenko.registration.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    RegisterUserDto userToRegisterUserDto(User user);

    User registerUserDtoToUser(RegisterUserDto registerUserDto);

}
