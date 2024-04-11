package ru.framuga;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookRepositoryJPA extends JpaRepository<Book, UUID> {

        Book findBookById(UUID id);

        void deleteBookById(UUID id);

}
