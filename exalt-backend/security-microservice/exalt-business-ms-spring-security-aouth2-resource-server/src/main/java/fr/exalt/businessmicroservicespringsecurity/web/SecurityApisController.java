package fr.exalt.businessmicroservicespringsecurity.web;

import fr.exalt.businessmicroservicespringsecurity.entities.dtos.RoleDto;
import fr.exalt.businessmicroservicespringsecurity.entities.dtos.UserDto;
import fr.exalt.businessmicroservicespringsecurity.entities.dtos.UserRoleDto;
import fr.exalt.businessmicroservicespringsecurity.entities.dtos.UserUpdateDto;
import fr.exalt.businessmicroservicespringsecurity.entities.models.Role;
import fr.exalt.businessmicroservicespringsecurity.entities.models.User;
import fr.exalt.businessmicroservicespringsecurity.exceptions.*;
import fr.exalt.businessmicroservicespringsecurity.services.UserService;
import fr.exalt.businessmicroservicespringsecurity.servicesecurity.securitywebtoken.JwtDto;
import fr.exalt.businessmicroservicespringsecurity.servicesecurity.securitywebtoken.JwtGeneratorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping(value = "api-security")
@AllArgsConstructor
public class SecurityApisController {
    private final UserService userService;
    private final JwtGeneratorService jwtGeneratorService;
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody UserDto dto) throws UserAlreadyExistsException,
            UserInformationInvalidException, PasswordsNotMatchException {
        User user = userService.createUser(dto);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @PostMapping("/roles")
    public ResponseEntity<Role> createRole(@RequestBody RoleDto dto) throws RoleInformationInvalidException, RoleAlreadyExistsException {
        return new ResponseEntity<>(userService.createRole(dto), HttpStatus.OK);
    }
    @PostMapping("/users/add-role")
    public ResponseEntity<User> userAddRole(@RequestBody UserRoleDto dto) throws UserNotFoundException, RoleNotFoundException,
            UserPossessThisRoleException {
        return new ResponseEntity<>(userService.userAddRole(dto), HttpStatus.OK);
    }
    @GetMapping( "/users")
    public ResponseEntity<Collection<User>> getAllUsers(){
        Collection<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    @GetMapping("/users/{userId}")
    public ResponseEntity<User> getUser(@PathVariable(name = "userId") long userId) throws UserNotFoundException {
        return new ResponseEntity<>(userService.getUser(userId), HttpStatus.OK);
    }
    @GetMapping("/roles")
    public ResponseEntity<Collection<Role>> getAllRoles(){
        return new ResponseEntity<>(userService.geAllRoles(), HttpStatus.OK);
    }
    @GetMapping("/roles/{roleId}")
    public ResponseEntity<Role> getRole(@PathVariable(name = "roleId") long roleId) throws RoleNotFoundException {
        return new ResponseEntity<>(userService.getRole(roleId), HttpStatus.OK);
    }
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody JwtDto jwtDto) throws UserAuthenticationFailedException,
            RefreshTokenMissException {
        return jwtGeneratorService.generateJwt(jwtDto);
    }
    @PostMapping("/users/remove-role")
    public ResponseEntity<User> userRemoveRole(@RequestBody UserRoleDto dto) throws UserNotFoundException, RoleNotFoundException, RoleNoAssignedTheUserException {
        return new ResponseEntity<>(userService.removeUserRole(dto),HttpStatus.OK);
    }
    @DeleteMapping("/users/{userId}")
    public void deleteUser(@PathVariable(name = "userId") Integer userId) throws UserNotFoundException {
        userService.deleteUser(userId);
    }
    @PutMapping(value = "/users/{userId}")
    public ResponseEntity<User> editUserInformation(@PathVariable(name = "userId") long userId, @RequestBody UserUpdateDto dto) throws UserNotFoundException {
        return new ResponseEntity<>(userService.editUserInformation(userId, dto), HttpStatus.OK);
    }
}
