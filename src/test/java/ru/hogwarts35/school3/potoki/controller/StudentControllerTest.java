package ru.hogwarts35.school3.potoki.controller;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ru.hogwarts35.school3.potoki.Application;
import ru.hogwarts35.school3.potoki.model.Faculty;
import ru.hogwarts35.school3.potoki.model.Student;
import ru.hogwarts35.school3.potoki.repository.FacultyRepository;
import ru.hogwarts35.school3.potoki.repository.StudentRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static java.lang.String.valueOf;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.result.StatusResultMatchersExtensionsKt.isEqualTo;

@SpringBootTest(classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class StudentControllerTest {

    @Autowired
    TestRestTemplate template;   // теперь мы можем писать тест.
    @Autowired
    FacultyRepository facultyRepository;

    @Autowired
    StudentRepository studentRepository;

    @AfterEach
    void clearDB() {
        studentRepository.deleteAll();
        facultyRepository.deleteAll();
    }

    @Test
    void create() {
        String name = "math";
        Integer age = 18;

        ResponseEntity<Student> response = createStudent(name, age);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);     // пишем проверки статус 200
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("math");
        assertThat(response.getBody().getAge()).isEqualTo(18);
    }

    @Test
    void getById() {
        ResponseEntity<Student> response = createStudent("ivan", 10);
        Long studentId = response.getBody().getId();

        response = template.getForEntity("/student/" + studentId, Student.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);     // пишем проверки статус 200
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getAge()).isEqualTo(10);
        assertThat(response.getBody().getName()).isEqualTo("ivan");
    }

    @Test
    void update() {
        ResponseEntity<Student> response = createStudent("ivan", 12);
        Long studentId = response.getBody().getId();


        template.put("/student/" + studentId, new Student(null, "ivan", 10));
        response = template.getForEntity("/student/" + studentId, Student.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);     // пишем проверки статус 200
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getAge()).isEqualTo(10);
    }

    @Test
    void delete() {
        ResponseEntity<Student> response = createStudent("ivan", 17);
        Long studentId = response.getBody().getId();


        template.delete("/student/" + studentId);
        response = template.getForEntity("/student/" + studentId, Student.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);     // пишем проверки статус 200

    }
    @Test
    void getAll() {
        createStudent("ida", 15);
        createStudent("geor", 16);


        ResponseEntity<Collection> response = template.getForEntity
                ("/student/", Collection.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);     // пишем проверки статус 200
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isEqualTo(2);
    }


    private ResponseEntity<Student> createStudent(String name, Integer age) {
        ResponseEntity<Student> response = template.postForEntity
                ("/student",
                        new Student(null, name, age),  // сделан запрос
                        Student.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        return response;

    }
}
