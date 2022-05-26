package com.example.musicBeat.repositories;


import com.example.musicBeat.entity.ERole;
import com.example.musicBeat.entity.Role;
import com.example.musicBeat.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository repository;
    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void findByRole(){
        Role role=new Role(ERole.ROLE_USER);

        Optional<Role> actual= repository.findByRole(ERole.ROLE_USER);

        assertNotNull(actual.get());

    }
}
