package ru.framuga;

import com.netflix.discovery.EurekaClient;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

@Service
public class BookProvider {

    private final WebClient webClient;

    public BookProvider(ReactorLoadBalancerExchangeFilterFunction reactorLoadBalancerExchangeFilterFunction) {
        webClient = WebClient.builder()
                .filter(reactorLoadBalancerExchangeFilterFunction)
                .build();
    }

    public Book getBookById(UUID id) {
        Book book = webClient.get()
                .uri("http://book-service/book/"+id)
                .retrieve()
                .bodyToMono(Book.class)
                .block();
        return book;
    }

}