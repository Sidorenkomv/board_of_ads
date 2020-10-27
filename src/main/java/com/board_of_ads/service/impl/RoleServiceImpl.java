package com.board_of_ads.service.impl;


import com.board_of_ads.models.Role;
import com.board_of_ads.models.dto.RoleDto;
import com.board_of_ads.repository.RoleRepository;
import com.board_of_ads.service.interfaces.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }


    @Override
    public Role getRoleByName(String name) {
        return roleRepository.findRoleByName(name);
    }

    @Override
    public Set<Role> defaultRolesSet() {
        Set<Role> roles = new HashSet<>();
        roles.add(getRoleByName("USER"));
        return roles;
    }

    @Override
    public List<Role> allRolesFromDb() {
        return roleRepository.findAll();
    }

    @Override
    public List<RoleDto> rolesFromBaseToDto() {
        List<RoleDto> roleDtoList = new ArrayList<>();
        allRolesFromDb().forEach(e -> roleDtoList.add(new RoleDto(e.getId(), e.getName())));
        return roleDtoList;
    }


}
