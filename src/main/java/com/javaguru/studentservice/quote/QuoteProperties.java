package com.javaguru.studentservice.quote;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Component
@ConfigurationProperties(prefix = "quote")
@Validated
public class QuoteProperties {

    @Valid
    @NotNull
    private Urls urls;

    public Urls getUrls() {
        return urls;
    }

    public void setUrls(Urls urls) {
        this.urls = urls;
    }

    static class Urls {

        @NotEmpty
        private String randomQuote;

        @NotEmpty
        private String quoteById;

        public String getRandomQuote() {
            return randomQuote;
        }

        public void setRandomQuote(String randomQuote) {
            this.randomQuote = randomQuote;
        }

        public String getQuoteById() {
            return quoteById;
        }

        public void setQuoteById(String quoteById) {
            this.quoteById = quoteById;
        }
    }
}
