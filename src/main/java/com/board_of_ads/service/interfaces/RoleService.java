package com.board_of_ads.service.interfaces;

import com.board_of_ads.models.Role;
import com.board_of_ads.models.dto.RoleDto;

import java.util.List;
import java.util.Set;

public interface RoleService {

    Role saveRole(Role role);

    Role getRoleByName(String name);

    Set<Role> defaultRolesSet();

    List<Role> allRolesFromDb();

    List<RoleDto> rolesFromBaseToDto();

}
