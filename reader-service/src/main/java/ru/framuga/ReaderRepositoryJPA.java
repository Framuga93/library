package ru.framuga;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface ReaderRepositoryJPA extends JpaRepository<Reader, UUID> {

        Reader findReaderById(UUID id);

        void deleteReaderById(UUID id);

}
