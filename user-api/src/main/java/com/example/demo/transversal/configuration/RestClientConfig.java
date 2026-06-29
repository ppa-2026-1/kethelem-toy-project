package com.example.demo.transversal.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient ticketRestClient(@Value("${app.ticket.url}") String ticketUrl) {
        return RestClient.builder().baseUrl(ticketUrl).build();
    }
}
