package com.board_of_ads.service.impl;

import com.board_of_ads.models.User;
import com.board_of_ads.service.interfaces.AuthorizationService;
import com.board_of_ads.service.interfaces.UserService;
import lombok.Data;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@Data
public class AuthorizationServiceImpl implements AuthorizationService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String isValid(User userAuth) {
        User user = userService.getUserByEmail(userAuth.getEmail());
        if (user == null) {
            return "User not found!";
        }
        if (!passwordEncoder.matches(userAuth.getPassword(), user.getPassword())) {
            return "Incorrect password!";
        }
        login(user);
        return "OK";
    }

    @Override
    public void login(User user) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    public void updatePrincipalInAuthToken(User user) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<GrantedAuthority> updatedAuthorities = new ArrayList<>(auth.getAuthorities());
        //updatedAuthorities.add(...);
        //add your role here [e.g., new SimpleGrantedAuthority("ROLE_NEW_ROLE")]
        var aut = (Object) user;
        Authentication newAuth = new UsernamePasswordAuthenticationToken(aut, auth.getCredentials(), updatedAuthorities);
        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }
}
