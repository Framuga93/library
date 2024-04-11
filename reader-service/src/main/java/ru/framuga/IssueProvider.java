package ru.framuga;

import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class IssueProvider {
    private final WebClient webClient;

    public IssueProvider(ReactorLoadBalancerExchangeFilterFunction reactorLoadBalancerExchangeFilterFunction){
        webClient = WebClient.builder()
                .filter(reactorLoadBalancerExchangeFilterFunction)
                .build();
    }

    public List<Issue> getAllIssue(){
        return webClient.get()
                .uri("http://issuer-service/issue/all")
                .retrieve()
                .bodyToFlux(Issue.class)
                .collectList()
                .block();
    }
}
