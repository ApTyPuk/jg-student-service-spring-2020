package com.javaguru.studentservice.quote;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class QuoteService {

    private final RestTemplate restTemplate;
    private final QuoteProperties quoteProperties;

    QuoteService(RestTemplate restTemplate, QuoteProperties quoteProperties) {
        this.restTemplate = restTemplate;
        this.quoteProperties = quoteProperties;
    }

    public QuoteDto findRandomQuote() {
        QuoteDto quote = restTemplate.getForObject(quoteProperties.getUrls().getRandomQuote(), QuoteDto.class);
        System.out.println("Received quote response: " + quote);
        if (quote == null) {
            throw new IllegalStateException("Failed to retrieve quote");
        }
        return quote;
    }
}
