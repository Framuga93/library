package ru.framuga;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ReaderService {

    private final IssueProvider issueProvider;
    private final ReaderRepositoryJPA readerRepository;


    public List<Issue> readerIssue(Reader reader) {
        List<Issue> readerIssueList = issueProvider.getAllIssue()
                .stream()
                .filter(it -> Objects.equals(it.getReader(), reader))
                .toList();
        if (readerIssueList.size() < 1)
            throw new NullPointerException("У читателя нет взятых книг");
        return readerIssueList;
    }

    public Reader findReaderById(UUID id) {
        Reader responseReader = readerRepository.findReaderById(id);
        if (responseReader == null)
            throw new NoSuchElementException("Читателя с таким ID не найдено");
        return responseReader;
    }

    @Transactional
    public void removeReaderFromRep(UUID id) {
        readerRepository.deleteReaderById(id);
    }

    public void addReaderToRep(Reader requestReader) {
        readerRepository.save(requestReader);
    }

    public List<Reader> getAllReaders() {
        List<Reader> readers = readerRepository.findAll();
        if(readers.size() < 1)
            throw new NoSuchElementException("Список читателей пуст");
        return readers;
    }
}
