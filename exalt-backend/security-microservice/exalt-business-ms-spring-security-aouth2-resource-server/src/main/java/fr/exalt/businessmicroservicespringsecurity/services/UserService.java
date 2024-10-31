package fr.exalt.businessmicroservicespringsecurity.services;

import fr.exalt.businessmicroservicespringsecurity.entities.dtos.RoleDto;
import fr.exalt.businessmicroservicespringsecurity.entities.dtos.UserDto;
import fr.exalt.businessmicroservicespringsecurity.entities.dtos.UserRoleDto;
import fr.exalt.businessmicroservicespringsecurity.entities.dtos.UserUpdateDto;
import fr.exalt.businessmicroservicespringsecurity.entities.models.Role;
import fr.exalt.businessmicroservicespringsecurity.entities.models.User;

import java.util.Collection;

public interface UserService {
    User createUser(UserDto userDto);
    User userAddRole(UserRoleDto dto);
    Collection<User> getAllUsers();
    User getUser(long userId) ;
    Role createRole(RoleDto dto);
    Collection<Role> geAllRoles();
    Role getRole(long roleId);

    User removeUserRole(UserRoleDto dto);

    void deleteUser(Integer userId);

    User editUserInformation(long userId, UserUpdateDto dto);
}
