package ru.framuga;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

@Service
public class ReaderProvider {
    private final WebClient webClient;

    public ReaderProvider(ReactorLoadBalancerExchangeFilterFunction reactorLoadBalancerExchangeFilterFunction) {
        webClient = WebClient.builder()
                .filter(reactorLoadBalancerExchangeFilterFunction)
                .build();
    }

    public Reader getReaderById(UUID id) {
        Reader reader = webClient.get()
                .uri("http://reader-service/reader/"+id)
                .retrieve()
                .bodyToMono(Reader.class)
                .block();
        return reader;
    }

}
