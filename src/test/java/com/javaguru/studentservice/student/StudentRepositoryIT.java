package com.javaguru.studentservice.student;

import com.javaguru.studentservice.student.domain.StudentEntity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // для реальной БД
class StudentRepositoryIT {

    @Autowired
    private StudentRepository victim;

    @Test
    void shouldSaveStudent() {
        victim.save(studentEntity());
    }

    @Test
    void shouldFindById() {
        StudentEntity createdEntity = studentEntity();
        victim.save(createdEntity);

        Optional<StudentEntity> foundEntity = victim.findById(createdEntity.getId());

        assertThat(foundEntity).contains(createdEntity);
    }

    private StudentEntity studentEntity() {
        StudentEntity entity = new StudentEntity();
        entity.setId(UUID.randomUUID().toString());
        entity.setQuote("TEST QUOTE");
        entity.setName("TEST NAME");
        entity.setLastName("TEST LASTNAME");
        return entity;
    }

}