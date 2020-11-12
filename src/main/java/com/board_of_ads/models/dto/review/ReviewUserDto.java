package com.board_of_ads.models.dto.review;

import com.board_of_ads.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewUserDto {
    private String firstName;
    private String lastName;
    private String avatarUrl;

    public ReviewUserDto(User user){
        this.firstName = user.getFirsName();
        this.lastName = user.getLastName();
        this.avatarUrl = user.getAvatar().getPathURL();
    }
}
