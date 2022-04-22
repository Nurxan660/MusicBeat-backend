package com.example.musicBeat.repository;


import com.example.musicBeat.entity.ERole;
import com.example.musicBeat.entity.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role,Long> {
    Optional<Role> findByRole(ERole role);
}
