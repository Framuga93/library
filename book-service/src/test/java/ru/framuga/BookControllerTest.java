package ru.framuga;

import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;
import java.util.UUID;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class BookControllerTest {

    @Autowired
    WebTestClient webTestClient;
    //todo: Подумать над использованием jdbctemplate
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    BookRepositoryJPA bookRepositoryJPA;

    @Data
    static class JUnitBookResponse {
        private UUID id;
        private String name;
    }

    Book testBook1 = new Book("test1");
    Book testBook2 = new Book("test2");

    @BeforeEach
    void clean() {
        bookRepositoryJPA.deleteAll();
    }


    @Test
    void testGetBookByIdSuccess(){

        Book expectedBook = bookRepositoryJPA.save(testBook1);


        JUnitBookResponse response = webTestClient.get()
                .uri("/book/" + expectedBook.getId())
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(JUnitBookResponse.class)
                .returnResult().getResponseBody();


        Assertions.assertNotNull(response);
        Assertions.assertEquals(expectedBook.getId(), response.getId());
        Assertions.assertEquals(expectedBook.getName(), response.getName());
    }

    @Test
    void testGetBookByIdNotFound() {
        UUID randomUUID = UUID.randomUUID();

        webTestClient.get()
                .uri("/book/" + randomUUID)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testDeleteBookSucces() {
        Book book = bookRepositoryJPA.save(testBook1);

        webTestClient.delete()
                .uri("/book/{id}", book.getId())
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testDeleteBookNotFound() {
        webTestClient.delete()
                .uri("/book/{id}", UUID.randomUUID())
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testCreateBookSuccessNotNull() {
        JUnitBookResponse bookResponse = new JUnitBookResponse();
        bookResponse.setId(UUID.randomUUID());
        bookResponse.setName("test");

        JUnitBookResponse response = webTestClient.post()
                .uri("/book")
                .bodyValue(bookResponse)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(JUnitBookResponse.class)
                .returnResult().getResponseBody();

        Assertions.assertNotNull(response);

        Assertions.assertTrue(bookRepositoryJPA.findById(response.getId()).isPresent());
    }

    @Test
    void testCreateBookNull() {
        JUnitBookResponse bookResponse = new JUnitBookResponse();

        webTestClient.post()
                .uri("/book")
                .bodyValue(bookResponse)
                .exchange()
                .expectStatus().isNoContent();

        Assertions.assertNull(bookRepositoryJPA.findBookById(bookResponse.getId()));
    }

    @Test
    void testGetAllBooks() {

        bookRepositoryJPA.saveAll(List.of(testBook1,testBook2));

        List<Book> expectedList = bookRepositoryJPA.findAll();

        List<JUnitBookResponse> responseList = webTestClient.get()
                .uri("/book/all")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(JUnitBookResponse.class)
                .returnResult().getResponseBody();

        Assertions.assertEquals(expectedList.size(), responseList.size());
    }

    //todo: сделать тест на getAllBooks если книг нет
}