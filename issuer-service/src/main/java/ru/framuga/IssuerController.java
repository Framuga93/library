package ru.framuga;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/issue")
public class IssuerController {


  private final IssuerService service;


  @PostMapping
  @Operation(summary = "create issue", description = "Выдать книгу читателю")
  public ResponseEntity<Issue> issueBook(@RequestBody IssueRequest request) {
    log.info("Получен запрос на выдачу: readerId = {}, bookId = {}", request.getReaderId(), request.getBookId());

    final Issue issue;
    try {
      issue = service.issue(request);
    } catch (NoSuchElementException e) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.status(HttpStatus.ACCEPTED).body(issue);
  }

  @PutMapping("/{id}")
  @Operation(summary = "return book", description = "Вернуть книгу от читателя")
  public void returnBook(@PathVariable UUID id){
    Issue issue = service.findIssueById(id);
    issue.setReturnedAt(LocalDateTime.now());
    service.saveIssue(issue);
  }

  @GetMapping("/{id}")
  @Operation(summary = "get issue by Id", description = "Находит и возвращает " +
          "выдачу книги по ID")
  public Issue getIssue(@PathVariable UUID id){
    return service.findIssueById(id);
  }

  @GetMapping("/all")
  @Operation(summary = "get all issues by Id", description = "Находит и возвращает " +
          "все выдачи книг")
  public List<Issue> getAllIssue(){
    return service.issueList();
  }

}
