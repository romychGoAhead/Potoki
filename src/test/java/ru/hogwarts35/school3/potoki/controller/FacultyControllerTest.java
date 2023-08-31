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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class FacultyControllerTest {

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
        String color = "blu";

        ResponseEntity<Faculty> response = createFaculty(name, color);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);     // пишем проверки статус 200
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("math");
        assertThat(response.getBody().getColor()).isEqualTo("blu");
    }

    @Test
    void getById() {
        ResponseEntity<Faculty> response = createFaculty("math", "red");
        Long facultyId = response.getBody().getId();

        response = template.getForEntity("/faculty/" + facultyId, Faculty.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);     // пишем проверки статус 200
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getColor()).isEqualTo("red");
        assertThat(response.getBody().getName()).isEqualTo("math");
    }

    @Test
    void update() {
        ResponseEntity<Faculty> response = createFaculty("math", "blu");
        Long facultyId = response.getBody().getId();


        template.put("/faculty/" + facultyId, new Faculty(null, "math", "red"));
        response = template.getForEntity("/faculty/" + facultyId, Faculty.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);     // пишем проверки статус 200
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getColor()).isEqualTo("red");
    }

    @Test
    void delete() {
        ResponseEntity<Faculty> response = createFaculty("math", "blu");
        Long facultyId = response.getBody().getId();


        template.delete("/faculty/" + facultyId);
        response = template.getForEntity("/faculty/" + facultyId, Faculty.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);     // пишем проверки статус 200

    }

    @Test
    void getAll() {
        createFaculty("math", "red");
        createFaculty("geography", "blu");


        ResponseEntity<Collection> response = template.getForEntity
                ("/faculty/", Collection.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);     // пишем проверки статус 200
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isEqualTo(2);
    }

    @Test
    void filteredByColorOrName() {
        String color = "red";
        createFaculty("math", "red");
        createFaculty("geography", "blu");


        ResponseEntity<ArrayList> response = template.getForEntity
                ("/faculty/by-color-or-name?colorOrName=" + color, ArrayList.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);     // пишем проверки статус 200
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isEqualTo(1);
        Map<String, String> next = (HashMap) response.getBody().iterator().next();
        assertThat(next.get("color")).isEqualTo("red");
    }

    @Test
    void byStudent() {

        ResponseEntity<Faculty> response = createFaculty("math", "red");
        Faculty faculty = response.getBody();
        Student student = new Student(null, "alex", 18);
        student.setFaculty(faculty);
        ResponseEntity<Student> studentResponse =
                template.postForEntity("/student", student, Student.class);
        assertThat(studentResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Long studentId = studentResponse.getBody().getId();

       response = template.getForEntity("/faculty/by-student?studentId=" + studentId, Faculty.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isEqualTo(faculty);
    }

    private ResponseEntity<Faculty> createFaculty(String name, String color) {
        ResponseEntity<Faculty> response = template.postForEntity
                ("/faculty",
                        new Faculty(null, name, color),  // сделан запрос
                        Faculty.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        return response;

    }




}

