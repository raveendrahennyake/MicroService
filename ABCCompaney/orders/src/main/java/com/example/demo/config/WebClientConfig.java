package com.example.demo.config;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Bean
    public WebClient webClient (){
        return WebClient.builder().build();
    }
    @Bean
    @Qualifier("inventoryWebClient")
    public WebClient inventoryWebClient (){
        return WebClient.builder().baseUrl("http://localhost:8081/api/v1").build();
    }
    @Bean
    @Qualifier("ProductWebClient")
    public WebClient ProductWebClient (){
        return WebClient.builder().baseUrl("http://localhost:8082/api/v1").build();
    }
}
