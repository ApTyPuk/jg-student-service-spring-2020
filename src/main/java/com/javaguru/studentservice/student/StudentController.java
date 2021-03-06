package com.javaguru.studentservice.student;

import com.javaguru.studentservice.student.dto.StudentCreateRequest;
import com.javaguru.studentservice.student.dto.StudentResponse;
import com.javaguru.studentservice.student.dto.StudentUpdateRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping("/students")
public class StudentController {

    private static final Logger log = LoggerFactory.getLogger(StudentController.class);

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<StudentResponse> findAllStudents() {
        log.info("Received find all students request");
        return studentService.findAllStudents();
    }

    @GetMapping("/{id}")
    public StudentResponse findStudentById(@PathVariable String id) {
        log.info("Received find student by id request, id: {}", id);
        return studentService.findById(id);
    }

    @PostMapping
    public ResponseEntity<StudentResponse> createStudent(@Valid @RequestBody StudentCreateRequest request, UriComponentsBuilder builder) {
        log.info("Received create student request: {}", request);
        StudentResponse response = studentService.save(request);
        return ResponseEntity.created(
                builder.path("/students/{id}")
                        .buildAndExpand(response.getId())
                        .toUri()
        )
                .body(response);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void updateStudent(@PathVariable String id, @Valid @RequestBody StudentUpdateRequest request) {
        log.info("Received request update student: {}", request);
        studentService.update(request, id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        log.info("Received delete student by id: {}", id);
        studentService.deleteStudentById(id);
    }

}
