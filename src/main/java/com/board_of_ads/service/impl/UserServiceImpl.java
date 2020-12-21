package com.board_of_ads.service.impl;

import com.board_of_ads.models.Image;
import com.board_of_ads.models.User;
import com.board_of_ads.models.dto.UserDto;
import com.board_of_ads.repository.UserRepository;
import com.board_of_ads.service.interfaces.CityService;
import com.board_of_ads.service.interfaces.RoleService;
import com.board_of_ads.service.interfaces.UserService;
import com.board_of_ads.util.BindingResultLogs;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final BindingResultLogs bindingResultLogs;
    private final CityService cityService;

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->new RuntimeException("User identity " + id + " not found!"));
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @Override
    public List<UserDto> getAllUsersDto() {
        List<UserDto> userDtoList = userRepository.findAllDto();
        return userDtoList;
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User regUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAvatar(new Image(null, "images/user.jpg"));
        user.setRoles(roleService.defaultRolesSet());
        return userRepository.save(user);
    }

    @Override
    public boolean checkUserDataBeforeReg(User user, BindingResult bindingResult, Logger logger) {
        if (getUserByEmail(user.getEmail()) == null
                && bindingResultLogs.checkUserFields(bindingResult, logger)) {
            regUser(user);
            return true;
        }
        return false;
    }

    @Override
    public User update(User principal, UserDto user) throws Exception {
        if (!user.getEmail().equals("")) {
            return changeEmail(principal, user);
        } else if (!user.getFirstName().equals("")) {
            User userOk = changeFirstNameOrCity(principal, user);
            return userOk;
        } else  if (!user.getPhone().equals("")) {
            return changePhoneNumber(principal, user);
        } else {
            return changePassword(principal, user);
        }
    }

    private User changePhoneNumber(User principal, UserDto user) {
        log.info("change phoneNumber for user with id : {}", principal.getId());
        var userFromDB = userRepository.findByEmail(principal.getEmail());
        userFromDB.setPhone(user.getPhone());
        return userRepository.save(userFromDB);
    }

    private User changeEmail(User principal, UserDto user) throws Exception {
        log.info("change email for user with id : {}", principal.getId());
        var userFromDB = userRepository.findByEmail(principal.getEmail());
        if (passwordEncoder.matches(user.getPassword(), userFromDB.getPassword())) {
            userFromDB.setEmail(user.getEmail());
            return userRepository.save(userFromDB);
        } else {
            throw new Exception("Password invalid");
        }
    }

    private User changeFirstNameOrCity(User principal, UserDto user) throws Exception {
        log.info("change name or city for user with id : {}", principal.getId());
        try {
            var userFromDB = userRepository.findByEmail(principal.getEmail());
            if (!userFromDB.getFirstName().equals(user.getFirstName())) {
                userFromDB.setFirstName(user.getFirstName());
            }
            if (user.getCityId() > 0) {
                var city = cityService.findCityById(user.getCityId());
                userFromDB.setCity(city.get());
            }
            User userOk =userRepository.save(userFromDB);
            return userOk;
        } catch (Exception e) {
            throw new Exception("Data not changed");
        }
    }

    private User changePassword(User principal, UserDto user) throws Exception {
        log.info("change password for user with id : {}", principal.getId());
        var userFromDB = userRepository.findByEmail(principal.getEmail());
        if (passwordEncoder.matches(user.getPassword(), userFromDB.getPassword())) {
            userFromDB.setPassword(passwordEncoder.encode(user.getNewPassword()));
            return userRepository.save(userFromDB);
        } else {
            throw new Exception("Password invalid");
        }
    }
}
