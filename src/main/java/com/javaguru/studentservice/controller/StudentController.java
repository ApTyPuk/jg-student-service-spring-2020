package com.javaguru.studentservice.controller;

import com.javaguru.studentservice.dto.StudentDto;
import com.javaguru.studentservice.service.StudentService;
import com.javaguru.studentservice.validation.StudentNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/students")
public class StudentController {


    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<StudentDto> findAllStudents() {
        List<StudentDto> studentDtos = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            studentDtos.add(new StudentDto(
                    UUID.randomUUID().toString(),
                    "TEST_USER_FIRST_NAME_" + i,
                    "TEST_USER_LAST_NAME_" + i
            ));
        }
        return studentDtos;
    }

    @GetMapping("/{id}")
    public StudentDto findStudentById(@PathVariable String id) {
        System.out.println("Received find student by id request, id: " + id);
        return studentService.findById(id);
    }

    @PostMapping
    public ResponseEntity<StudentDto> createStudent(@RequestBody StudentDto studentDto, UriComponentsBuilder builder) {
        System.out.println("Received create student request: " + studentDto);
        StudentDto response = studentService.save(studentDto);
        return ResponseEntity.created(
                builder.path("/students/{id}")
                        .buildAndExpand(response.getId()).toUri()).build();
    }

    @PutMapping("/{id}")
    public void updateStudent(@PathVariable String id, @RequestBody StudentDto studentDto) {
        System.out.println("Received id: " + id);
        System.out.println("Received request update student: " + studentDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        System.out.println("Received delete student by id " + id);
        studentService.deleteStudentById(id);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler
    public void handleNotFound(StudentNotFoundException exception) {
        System.out.println(exception.getMessage());
    }

}
