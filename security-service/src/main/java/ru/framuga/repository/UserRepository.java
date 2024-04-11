package ru.framuga.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.framuga.homework.model.User;
import ru.framuga.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLogin(String login);
}
