package ru.framuga;

import com.netflix.discovery.converters.Auto;
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

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class ReaderControllerTest {

    @Autowired
    WebTestClient webTestClient;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    ReaderRepositoryJPA readerRepositoryJPA;

    @Data
    static class JUnitReaderResponse{
        private UUID id;
        private String name;
    }

    Reader testReader1 = new Reader("test1");
    Reader testReader2 = new Reader("test2");

    @BeforeEach
    void clean(){
        readerRepositoryJPA.deleteAll();
    }

    @Test
    void testGetBookById() {

        Reader expectedReader = readerRepositoryJPA.save(testReader1);

        JUnitReaderResponse response = webTestClient.get()
                .uri("/reader/{id}", expectedReader.getId())
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(JUnitReaderResponse.class)
                .returnResult().getResponseBody();

        Assertions.assertNotNull(response);
        Assertions.assertEquals(expectedReader.getId(), response.getId());
        Assertions.assertEquals(expectedReader.getName(), response.getName());
    }

    @Test
    void testGetReaderNotFound(){
        UUID randomUUID = UUID.randomUUID();

        webTestClient.get()
                .uri("/book/{id}", randomUUID)
                .exchange()
                .expectStatus().isNotFound();

    }

    //todo: спросить у преподавателя про возможность вызова ресурса
    @Test
    void testGetReaderIssue() {
    }

    @Test
    void testGetAllReaders() {

        readerRepositoryJPA.saveAll(List.of(testReader1, testReader2));

        List<Reader> expectedList = readerRepositoryJPA.findAll();

        List<JUnitReaderResponse> responseList = webTestClient.get()
                .uri("/reader/all")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(JUnitReaderResponse.class)
                .returnResult().getResponseBody();

        Assertions.assertEquals(expectedList.size(), responseList.size());
    }

    @Test
    void testDeleteReaderSuccess() {
        Reader reader = readerRepositoryJPA.save(testReader1);

        webTestClient.delete()
                .uri("/reader/{id}", reader.getId())
                .exchange()
                .expectStatus().isOk();

    }

    @Test
    void testDeleteReaderNotFound(){
        webTestClient.delete()
                .uri("/reader/{id}", UUID.randomUUID())
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void createReader() {
    }
}