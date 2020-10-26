package com.board_of_ads.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String email;
    private String password;
    private String newPassword;
    private String firstName;
    private String phone;
    private Long cityId;
}