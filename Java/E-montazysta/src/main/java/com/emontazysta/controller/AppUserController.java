package com.emontazysta.controller;

import com.emontazysta.Role;
import com.emontazysta.mapper.UserMapper;
import com.emontazysta.model.AppUser;
import com.emontazysta.model.dto.AppUserDto;
import com.emontazysta.service.impl.AppUserServiceImpl;
import com.emontazysta.service.impl.RoleServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import static com.emontazysta.configuration.Constants.API_BASE_CONSTANT;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = API_BASE_CONSTANT+"/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class AppUserController {

    private final AppUserServiceImpl userService;
    private final RoleServiceImpl roleServiceImpl;

    @GetMapping("/all")
    @Operation(description = "Allows to get all Users.", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<List<AppUserDto>> getAppUsers() {
        return ResponseEntity.ok().body(userService.getAll().stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList()));
    }

    @PostMapping("/create")
    @Operation(description = "Allows to add new User.", security = @SecurityRequirement(name = "bearer-key"))
    @Secured(value = {"ADMIN","CLOUD_ADMIN"})
    public ResponseEntity<AppUser> saveAppUser(@RequestBody final AppUser user, Principal principal) {
        List<Role> roles = userService.findByUsername(principal.getName()).getRoles();
        if (roles.contains(Role.CLOUD_ADMIN)) {
            if (userService.findByUsername(user.getUsername()) == null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(userService.add(user));
            } else {
                log.info("User {} already exists", user.getUsername());
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
        }else if(roles.contains(Role.ADMIN)
                && !(roles.contains(Role.CLOUD_ADMIN))
                && !user.getRoles().contains(Role.CLOUD_ADMIN)) {
            if (userService.findByUsername(user.getUsername()) == null ) {
                return ResponseEntity.status(HttpStatus.CREATED).body(userService.add(user));
            } else {
                log.info("User {} already exists", user.getUsername());
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
        } else {
            log.info("User {} does not have the required permissions", principal.getName());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PostMapping("/{id}/update")
    @Operation(description = "Allows to update User.", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<AppUser> updateAppUser(@PathVariable Long id, @RequestBody final AppUser user) {
        if(userService.getById(id) != null) {
            return ResponseEntity.status(HttpStatus.OK).body(userService.update(id,user));
        }else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
    @GetMapping("/{id}")
    @Operation(description = "Allows to get User by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public AppUserDto getUserById(@PathVariable Long id) {
        return UserMapper.toDto(userService.getById(id));
    }


    @PostMapping("/{id}/setroles")
    @Operation(description = "Allows to assign list of roles to the user", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<String> addRoleToUser(@RequestParam Long id, @RequestBody List<String> role) {
        ResponseEntity response = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        if (roleServiceImpl.getAllRoles().contains(role)
                && userService.getById(id) != null) {
            userService.setRolesToUser(id, role);
            response =  ResponseEntity.status(HttpStatus.OK).build();
        }else if (!roleServiceImpl.getAllRoles().contains(role)) {
            log.info("Can't add role '{}' no such user role", role);
        } else if (userService.getById(id) == null) {
            log.info("Can't add role '{}' can't find user with id {}", id);
        }
        return response;
    }

}
