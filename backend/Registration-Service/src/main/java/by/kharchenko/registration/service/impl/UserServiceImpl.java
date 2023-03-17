package by.kharchenko.registration.service.impl;

import by.kharchenko.registration.dto.*;
import by.kharchenko.registration.entity.Role;
import by.kharchenko.registration.entity.User;
import by.kharchenko.registration.exception.UsernameAlreadyExistsException;
import by.kharchenko.registration.mapper.UserMapper;
import by.kharchenko.registration.repository.RoleRepository;
import by.kharchenko.registration.repository.UserRepository;
import by.kharchenko.registration.service.UserService;
import by.kharchenko.registration.util.encoder.CustomPasswordEncoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final String gitHubClientId;
    private final String gitHubClientSecret;
    private final String faceBookClientId;
    private final String faceBookClientSecret;

    public UserServiceImpl(UserRepository userRepository
            , RoleRepository roleRepository
            ,@Value("${github.client-id}") String gitHubClientId
            ,@Value("${github.client-secret}") String gitHubClientSecret
            ,@Value("${facebook.client-id}") String faceBookClientId
            ,@Value("${facebook.client-secret}") String faceBookClientSecret) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.gitHubClientId = gitHubClientId;
        this.gitHubClientSecret = gitHubClientSecret;
        this.faceBookClientId = faceBookClientId;
        this.faceBookClientSecret = faceBookClientSecret;
    }

    @Override
    public void add(RegisterUserDto userDto) throws UsernameAlreadyExistsException {
        Optional<User> optionalUser = userRepository.findByUsername(userDto.getUsername());
        if (!optionalUser.isPresent()) {
            User user = UserMapper.INSTANCE.registerUserDtoToUser(userDto);
            user.setPassword(CustomPasswordEncoder.encode(user.getPassword()));
            Role role = roleRepository.findById(1L).get();
            Set<Role> roles = new HashSet<>();
            roles.add(role);
            user.setRoles(roles);
            userRepository.save(user);
        }else {
            throw new UsernameAlreadyExistsException("Username is already exists");
        }
    }

    @Override
    public ResultUserAfterRegisterDto addFromGitHub(GitHubUser gitHubUser) {

        User user = new User();
        user.setUsername(gitHubUser.getLogin());
        user.setPassword(UUID.randomUUID().toString());
        user.setGitHubId(gitHubUser.getId());
        user.setEmail(gitHubUser.getEmail());
        user.setRoles(getDefaultRoles());

        User savedUser = userRepository.save(user);

        return new ResultUserAfterRegisterDto(savedUser.getId());
    }

    @Override
    public ResultUserAfterRegisterDto addFromFaceBook(FaceBookUser faceBookUser) {
        User user = new User();
        user.setUsername(faceBookUser.getName().split(" ")[0]);
        user.setPassword(UUID.randomUUID().toString());
        user.setFacebookId(faceBookUser.getId());
        user.setEmail(faceBookUser.getEmail());
        user.setRoles(getDefaultRoles());

        User savedUser = userRepository.save(user);

        return new ResultUserAfterRegisterDto(savedUser.getId());
    }

    private Set<Role> getDefaultRoles(){
        Role role = roleRepository.findById(1L).get();
        Set<Role> roles = new HashSet<>();
        roles.add(role);

        return roles;
    }

}
