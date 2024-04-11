package ru.framuga;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.framuga.aspect.annotation.Timer;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/book")
@Tag(name = "Controllers")
public class BookController {


    private final BookService bookService;

    @GetMapping("/{id}")
    @Operation(summary = "get book by Id", description = "Находит и возвращает " +
            "книгу по ID")
    public ResponseEntity<Book> getBookById(@PathVariable("id") UUID id){
        Book responseBook;
        try {
            responseBook = bookService.findBookByID(id);
        }catch (NoSuchElementException e){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseBook);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete book from repository", description = "Удалить книгу из репозитория по ID")
    public ResponseEntity<Book> deleteBook(@PathVariable("id") UUID id){
        try {
            bookService.removeBookFromRep(id);
        }catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping
    @Operation(summary = "add book in repository", description = "Добавить книгу в репозиторий")
    public ResponseEntity<Book> createBook(@RequestBody Book requestBook){
        Book responseBook;
        try {
            responseBook = bookService.addBookToRep(requestBook);
        }catch (NullPointerException e){
            return ResponseEntity.noContent().build();
        }
        log.info("Создана книга: bookName = {}", requestBook.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseBook);
    }

    @Timer
    @GetMapping("/all")
    public ResponseEntity<List<Book>> getAllBooks(){
        List<Book> books;
        try{
            books = bookService.getAllBooks();
        }catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(books);
    }

}
