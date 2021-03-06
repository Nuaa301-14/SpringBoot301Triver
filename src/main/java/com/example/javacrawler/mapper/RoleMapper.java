package com.example.javacrawler.mapper;

import com.example.javacrawler.entity.Privilege;
import com.example.javacrawler.entity.Role;
import com.example.javacrawler.entity.UserRole;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleMapper {

    List<Role> findRolesByUserId(int userId);

    List<Privilege> findPrivilegesByRoleId(int roleId);

    void insertRole(UserRole role);
}
