package com.board_of_ads.controllers.rest;

import com.board_of_ads.models.User;
import com.board_of_ads.models.dto.RoleDto;
import com.board_of_ads.service.interfaces.RoleService;
import com.board_of_ads.service.interfaces.UserService;
import com.board_of_ads.util.BindingResultLogs;
import com.board_of_ads.util.Response;
import com.board_of_ads.util.SuccessResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/admin/")
@AllArgsConstructor
@Slf4j
public class AdminRestController {

    private final UserService userService;
    private final BindingResultLogs bindingResultLogs;
    private final RoleService roleService;

    @PostMapping(value = "/newUser")
    public Response<User> createUser(@RequestBody @Valid User user, BindingResult bindingResult) {
        log.info("Use this default logger");

        if (bindingResultLogs.checkUserFields(bindingResult, log)) {
            return new SuccessResponse<>(userService.saveUser(user));
        }
        return Response.error().code(HttpStatus.NOT_MODIFIED).text("Incorrect Data").build();
    }

    @PutMapping("/newUserData")
    public Response<User> updateUser(@RequestBody @Valid User user, BindingResult bindingResult) {
        if (bindingResultLogs.checkUserFields(bindingResult, log)) {
            return new SuccessResponse<>(userService.saveUser(user));
        }
        return Response.error().code(HttpStatus.NOT_MODIFIED).text("Incorrect Data").build();
    }

    @GetMapping("/allUsers")
    public Response<List<User>> getAllUsersList() {
        List<User> userList = userService.getAllUsers();
        return (userList.size() > 0)
                ? Response.ok(userList)
                : Response.error().code(HttpStatus.NOT_FOUND).text("Incorrect Data").build();
    }

    @GetMapping("/user/{id}")
    public Response<User> getUserById(@PathVariable(name = "id") Long id) {
        User user = userService.getUserById(id);
        return (user != null)
                ? Response.ok(user)
                : Response.error().code(HttpStatus.NOT_FOUND).text("Incorrect Data").build();
    }

    @DeleteMapping("/user/{id}")
    public Response<Void> deleteUserById(@PathVariable(name = "id") Long id) {
        userService.deleteUser(id);
        return new Response<>();
    }

    @GetMapping("/allRoles")
    public Response<List<RoleDto>> getAllRolesFromDB() {
        List<RoleDto> roleList = roleService.rolesFromBaseToDto();
        return (roleList.size() != 0)
                ? Response.ok(roleList)
                : Response.error().code(HttpStatus.NOT_FOUND).text("Incorrect Data").build();
    }

}
