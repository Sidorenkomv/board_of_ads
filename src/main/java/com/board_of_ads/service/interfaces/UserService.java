package com.board_of_ads.service.interfaces;

import com.board_of_ads.models.User;
import com.board_of_ads.models.dto.UserDto;
import org.slf4j.Logger;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface UserService {

    User getUserById(Long id);

    User getUserByEmail(String email);

    User saveUser(User user);

    List<User> getAllUsers();

    void deleteUser(Long id);

    User regUser(User user);

    boolean checkUserDataBeforeReg(User user, BindingResult bindingResult, Logger logger);

    User update(User principal, UserDto user) throws Exception;

    List<UserDto> getAllUsersDto();
}
