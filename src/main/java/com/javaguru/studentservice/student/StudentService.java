package com.javaguru.studentservice.student;

import com.javaguru.studentservice.quote.QuoteDto;
import com.javaguru.studentservice.quote.QuoteService;
import com.javaguru.studentservice.student.domain.StudentEntity;
import com.javaguru.studentservice.student.dto.StudentCreateRequest;
import com.javaguru.studentservice.student.dto.StudentResponse;
import com.javaguru.studentservice.student.dto.StudentUpdateRequest;
import com.javaguru.studentservice.student.validation.StudentNotFoundException;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository repository;
    private final QuoteService quoteService;

    StudentService(StudentRepository repository, QuoteService quoteService) {
        this.repository = repository;
        this.quoteService = quoteService;
    }

    public StudentResponse save(StudentCreateRequest request) {
        QuoteDto quoteDto = quoteService.findRandomQuote();

        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setId(UUID.randomUUID().toString());
        studentEntity.setName(request.getName());
        studentEntity.setLastName(request.getLastName());
        studentEntity.setQuote(quoteDto.getEnglishQuote());

        repository.save(studentEntity);

        return new StudentResponse(studentEntity.getId(), studentEntity.getName(), studentEntity.getLastName());
    }

    public StudentResponse findById(String id) {
        return repository.findById(id)
                .map(entity -> new StudentResponse(entity.getId(), entity.getName(), entity.getLastName()))
                .orElseThrow(() -> new StudentNotFoundException("Student not found, id=" + id));
    }


    public void deleteStudentById(String id) {
        if (!repository.existsById(id)) {
            throw new StudentNotFoundException("Student not found, id=" + id);
        }
        repository.deleteById(id);
    }

    public List<StudentResponse> findAllStudents() {
        return repository.findAll()
                .stream()
                .map(student -> new StudentResponse(student.getId(),
                        student.getName(),
                        student.getLastName()))
                .collect(Collectors.toList());
    }

    public void update(StudentUpdateRequest request, String id) {
        StudentEntity entity = repository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found, id=" + id));
        entity.setName(request.getName());
        entity.setLastName(request.getLastName());
        repository.save(entity);
    }
}
