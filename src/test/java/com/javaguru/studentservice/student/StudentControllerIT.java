package com.javaguru.studentservice.student;

import com.javaguru.studentservice.quote.QuoteDto;
import com.javaguru.studentservice.student.domain.StudentEntity;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

import javax.transaction.Transactional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class StudentControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentRepository repository;

    @MockBean
    private RestTemplate restTemplate;

    @Test
    void shouldFindStudentById() throws Exception {
        StudentEntity entity = studentEntity("TEST NAME");
        repository.save(entity);

        mockMvc.perform(get("/students/" + entity.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(entity.getId()))
                .andExpect(jsonPath("$.name").value(entity.getName()))
                .andExpect(jsonPath("$.lastName").value(entity.getLastName()))
                .andExpect(jsonPath("$.quote").value(entity.getQuote()));
    }

    @Test
    void shouldFindAllStudents() throws Exception {
        StudentEntity firstStudent = studentEntity("TEST NAME 1");
        repository.save(firstStudent);
        StudentEntity secondStudent = studentEntity("TEST NAME 2");
        repository.save(secondStudent);

        mockMvc.perform(get("/students/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void shouldCreateStudent() throws Exception {
        when(restTemplate.getForObject(anyString(), eq(QuoteDto.class))).thenReturn(quoteDto());
//        when(restTemplate.getForObject("https://programming-quotes-api.herokuapp.com/quotes/random", QuoteDto.class)).thenReturn(quoteDto());

        mockMvc.perform(post("/students")
                .content(createStudentRequestJson())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value("Vasya"))
                .andExpect(jsonPath("$.lastName").value("Pupkin"))
                .andExpect(jsonPath("$.quote").value("Be happy"))
                .andExpect(header().exists("Location"));
    }

    private StudentEntity studentEntity(String name) {
        StudentEntity entity = new StudentEntity();
        entity.setId(UUID.randomUUID().toString());
        entity.setQuote("TEST QUOTE");
        entity.setName(name);
        entity.setLastName("TEST LASTNAME");
        return entity;
    }

    private QuoteDto quoteDto() {
        QuoteDto dto = new QuoteDto();
        dto.setAuthor("Test author");
        dto.setEnglishQuote("Be happy");
        dto.setExternalId("31231dasd21ea");
        dto.setSecondExternalId("31231dasd21ea");
        return dto;
    }

    private String createStudentRequestJson() throws JSONException {
        return new JSONObject()
                .put("name", "Vasya")
                .put("lastName", "Pupkin")
                .toString();
    }
}