package com.project.pescueshop.service;

import com.project.pescueshop.model.Role;
import com.project.pescueshop.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public List<Role> getDefaultUserRole(){
        return roleRepository.getDefaultUserRole();
    }
}
