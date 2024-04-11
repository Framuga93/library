package ru.framuga;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/reader")
public class ReaderController {


    private final ReaderService readerService;

    @GetMapping("/{id}")
    @Operation(summary = "get reader by Id", description = "Находит и возвращает " +
            "читателя по ID")
    public ResponseEntity<Reader> getBookById(@PathVariable("id") UUID id) {
        Reader responseReader;
        try {
            responseReader = readerService.findReaderById(id);
        }catch (NoSuchElementException e){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseReader);
    }

    @GetMapping("/{id}/issue")
    @Operation(summary = "get reader issue`s", description = "Находит и возвращает " +
            "выдачи книг читателю по ID")
    public ResponseEntity<List<Issue>> getReaderIssue(@PathVariable UUID id) {
        Reader reader = readerService.findReaderById(id);
        List<Issue> responseIssueList;
        try{
            responseIssueList = readerService.readerIssue(reader);
        }
        catch (NullPointerException e){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseIssueList);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Reader>> getAllReaders(){
        List<Reader> readers;
        try{
            readers = readerService.getAllReaders();
        }catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(readers);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete reader by Id", description = "Удалить " +
            "читателя по ID")
    public ResponseEntity<Reader> deleteReader(@PathVariable("id") UUID id) {
        try{
        readerService.removeReaderFromRep(id);
        }catch (NoSuchElementException e){
            return ResponseEntity.notFound().build();
        }
        log.info("Книга " + readerService.findReaderById(id).getName() + " удалена из репозитория");
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping
    @Operation(summary = "add reader to repository", description = "Добавить читателя в репозиторий")
    public void createReader(@RequestBody Reader requestReader) {
        readerService.addReaderToRep(requestReader);
        log.info("Создан читатель: readerName = {}", requestReader.getName());
    }


}
