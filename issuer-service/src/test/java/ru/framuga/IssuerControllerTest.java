package ru.framuga;


import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class IssuerControllerTest {

    @Autowired
    WebTestClient webTestClient;
    @Autowired
    IssueRepositoryJPA repositoryJPA;

    @Data
    static class JUnitIssueResponse{
        private UUID id;
        private Book book;
        private Reader reader;
        private LocalDateTime issueAt;
        private LocalDateTime returnedAt;
    }


    @Test
    void getIssueByIdTest(){
//        Issue expectedIssue = repositoryJPA.save(???);

        JUnitIssueResponse issueResponse = webTestClient.get()
                .uri("/issue/"+expectedIssue.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(JUnitIssueResponse.class)
                .returnResult().getResponseBody();

        Assertions.assertNotNull(issueResponse);
        Assertions.assertEquals(expectedIssue.getId(),issueResponse.getId());
        Assertions.assertEquals(expectedIssue.getBook(),issueResponse.getBook());
    }


}