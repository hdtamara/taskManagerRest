package com.taskmanagerrest.taskmanager.repository;

import com.taskmanagerrest.taskmanager.entities.Rol;
import com.taskmanagerrest.taskmanager.enums.RolesList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol,Long> {
    Optional<Rol> findByRoleName(RolesList roleName);
}
