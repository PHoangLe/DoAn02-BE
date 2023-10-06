package com.project.pescueshop.repository;

import com.project.pescueshop.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    @Query("SELECT r FROM Role r " +
            "WHERE r.roleId = 2 ")
    public List<Role> getDefaultUserRole();
}
