package fr.exalt.businessmicroservicespringsecurity.services;

import fr.exalt.businessmicroservicespringsecurity.entities.dtos.UserRoleDto;
import fr.exalt.businessmicroservicespringsecurity.entities.dtos.UserUpdateDto;
import fr.exalt.businessmicroservicespringsecurity.entities.models.Role;
import fr.exalt.businessmicroservicespringsecurity.entities.models.User;
import fr.exalt.businessmicroservicespringsecurity.exceptions.*;
import fr.exalt.businessmicroservicespringsecurity.entities.dtos.RoleDto;
import fr.exalt.businessmicroservicespringsecurity.entities.dtos.UserDto;
import fr.exalt.businessmicroservicespringsecurity.repositories.RoleRepository;
import fr.exalt.businessmicroservicespringsecurity.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;
import java.util.Set;

@Service
@AllArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final MapperService mapperService;

    @Override
    public User createUser(UserDto userDto) {
        if (UserRoleValidator.isInvalid(userDto)) {
            throw new UserInformationInvalidException(ExceptionE.USER_INFO);
        }
        String username = UserRoleValidator.buildUsername(userDto.getFirstname(), userDto.getLastname());
       User user = userRepository.findByUsername(username);
        if(UserRoleValidator.exists(user)){
            throw new UserAlreadyExistsException(ExceptionE.USER_EXISTS);
        }
        if(!UserRoleValidator.passwordsMatch(userDto.getPwd(), userDto.getPwd1())){
            throw new PasswordsNotMatchException(ExceptionE.PASSWORD_MATCH);
        }
        User mappedUser = mapperService.from(userDto);
        mappedUser.setCreatedAt(Instant.now().toString());
        mappedUser.setUsername(username);
        mappedUser.setPwd(passwordEncoder.encode(userDto.getPwd()));
        return userRepository.save(mappedUser);
    }

    @Override
    public User userAddRole(UserRoleDto dto){
        User user = userRepository.findById(dto.getUserId()).orElseThrow(
                () -> new UserNotFoundException(ExceptionE.USER_NOT_FOUND));
        Role role = roleRepository.findByRoleName(dto.getRoleName());
        if (role == null)
            throw new RoleNotFoundException(ExceptionE.ROLE_NOT_FOUND);
        Set<Role> roles = user.getRoles();
        if(roles.contains(role))
            throw new UserPossessThisRoleException(ExceptionE.USER_POSSESS_ROLE);
        roles.add(role);
        user.setRoles(roles);
        return userRepository.save(user);
    }

    @Override
    public Collection<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(long userId) throws UserNotFoundException {
        return userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(ExceptionE.USER_NOT_FOUND));
    }

    @Override
    public Role createRole(RoleDto dto){
        if (UserRoleValidator.isInvalid(dto)) {
            throw new RoleInformationInvalidException(ExceptionE.ROLE_INFO);
        }
        if (!UserRoleValidator.isValid(dto.getRoleName()))
            throw new RoleInformationInvalidException(ExceptionE.ROLE_INFO);
        Role role = roleRepository.findByRoleName(dto.getRoleName());
        if(UserRoleValidator.exists(role))
            throw new RoleAlreadyExistsException(ExceptionE.ROLE_EXISTS);
        Role mappedRole = mapperService.from(dto);
        mappedRole.setCreatedAt(Instant.now().toString());
        return roleRepository.save(mappedRole);
    }

    @Override
    public Collection<Role> geAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role getRole(long roleId){
        return roleRepository.findById(roleId).orElseThrow(
                () -> new RoleNotFoundException(ExceptionE.ROLE_NOT_FOUND));
    }

    @Override
    public User removeUserRole(UserRoleDto dto) {
        User user = getUser(dto.getUserId());
        Role role = roleRepository.findByRoleName(dto.getRoleName());
        if(!UserRoleValidator.exists(role))
            throw new RoleNotFoundException(ExceptionE.ROLE_NOT_FOUND);
        if(!user.getRoles().contains(role))
            throw new RoleNoAssignedTheUserException(ExceptionE.USER_ROLE);
        user.getRoles().remove(role);
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Integer userId) {
        User user = getUser(userId);
        userRepository.delete(user);
    }

    @Override
    public User editUserInformation(long userId, UserUpdateDto dto){
        User user = getUser(userId);
        String username = UserRoleValidator.buildUsername(dto.getFirstname(), dto.getLastname());
        user.setUsername(username);
        user.setFirstname(dto.getFirstname());
        user.setLastname(dto.getLastname());
        user.setEmail(dto.getEmail());
        //unchangeable user values
        user.setUserId(userId);
        user.setPwd(user.getPwd());
        user.setCreatedAt(user.getCreatedAt());
        user.setRoles(user.getRoles());
        return userRepository.save(user);
    }
}
