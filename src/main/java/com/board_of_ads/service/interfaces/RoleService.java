package com.board_of_ads.service.interfaces;

import com.board_of_ads.model.Role;

public interface RoleService {

    Role saveRole(Role role);

    Role getRoleByName(String name);
}
