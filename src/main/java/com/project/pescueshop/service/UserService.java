package com.project.pescueshop.service;

import com.project.pescueshop.entity.Role;
import com.project.pescueshop.entity.User;
import com.project.pescueshop.repository.UserRepository;
import com.project.pescueshop.util.constant.EnumStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;

    public User findByEmail(String email){
        return userRepository.findByEmail(email).orElse(null);
    }

    public User addUser(User user){
        List<Role> userRoles = roleService.getDefaultUserRole();
        user.setUserRoles(userRoles);

        user.setStatus(EnumStatus.ACTIVE.value);

        return userRepository.save(user);
    }
}
