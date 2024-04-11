package ru.framuga.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.framuga.model.Roles;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Roles,Long> {

    Optional<Roles> findByName(String name);
}
