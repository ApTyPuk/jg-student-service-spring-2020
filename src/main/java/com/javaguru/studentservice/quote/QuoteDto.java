package com.javaguru.studentservice.quote;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class QuoteDto {

    @JsonProperty("id")
    private String externalId;
    @JsonProperty("en")
    private String englishQuote;
    private String author;
    @JsonProperty("_id")
    private String secondExternalId;

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getEnglishQuote() {
        return englishQuote;
    }

    public void setEnglishQuote(String englishQuote) {
        this.englishQuote = englishQuote;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSecondExternalId() {
        return secondExternalId;
    }

    public void setSecondExternalId(String secondExternalId) {
        this.secondExternalId = secondExternalId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuoteDto quoteDto = (QuoteDto) o;
        return Objects.equals(externalId, quoteDto.externalId) &&
                Objects.equals(englishQuote, quoteDto.englishQuote) &&
                Objects.equals(author, quoteDto.author) &&
                Objects.equals(secondExternalId, quoteDto.secondExternalId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(externalId, englishQuote, author, secondExternalId);
    }

    @Override
    public String toString() {
        return "QuoteDto{" +
                "externalId='" + externalId + '\'' +
                ", englishQuote='" + englishQuote + '\'' +
                ", author='" + author + '\'' +
                ", secondExternalId='" + secondExternalId + '\'' +
                '}';
    }
}
