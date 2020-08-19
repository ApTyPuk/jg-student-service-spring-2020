package com.javaguru.studentservice.student.dto;

import java.util.Objects;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

public class StudentDto {

    @Null(groups = Create.class)
    @NotNull(groups = Update.class)
    private String id;

    @NotEmpty(groups = {Create.class, Update.class})
    @Size(min = 2, max = 32, groups = {Create.class, Update.class})
    private String name;

    @NotEmpty(groups = {Create.class, Update.class})
    @Size(min = 2, max = 32, groups = {Create.class, Update.class})
    private String lastName;

    public StudentDto() {
    }

    public StudentDto(String id, String name, String lastName) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentDto that = (StudentDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(lastName, that.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, lastName);
    }

    @Override
    public String toString() {
        return "StudentDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    public interface Update {
    }

    public interface Create {
    }
}
