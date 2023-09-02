package ru.hogwarts35.school3.potoki.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts35.school3.potoki.model.Faculty;
import ru.hogwarts35.school3.potoki.model.Student;
import ru.hogwarts35.school3.potoki.repository.FacultyRepository;
import ru.hogwarts35.school3.potoki.repository.StudentRepository;
import ru.hogwarts35.school3.potoki.service.StudentService;

import javax.management.MXBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class) // указываем контроллер который тестируем
public class StudentControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired                    // в методе create объект student преобразовываем в JSON
    ObjectMapper objectMapper;

    @MockBean
    StudentRepository studentRepository;  // мокаем репозтории
    @MockBean
    FacultyRepository facultyRepository;
    @SpyBean
    StudentService studentService;   // компоненты которые не надо мокать

    @Test
    void getById() throws Exception {

        Student student = new Student(1L, "ivan", 15);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student)); // findById возвращает Optional

        mockMvc.perform(MockMvcRequestBuilders.get("/student/1")                           // вызываем эндпоин
                        .accept(MediaType.APPLICATION_JSON)                                                  // пишем тип данных которые принимает эндпоинт
                        .contentType(MediaType.APPLICATION_JSON))                                           // и возвращает
                .andExpect(status().isOk())                                                           // через andExpect делаем проверки, проверяем что статус 200
                .andExpect(jsonPath("$.name").value("ivan"))                                  // проверить данные
                .andExpect(jsonPath("$.age").value(15));

    }
    @Test
    void create() throws Exception {
        Student student = new Student(1L, "ivan", 15);
        when(studentRepository.save(ArgumentMatchers.any(Student.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders.post("/student")                           // вызываем эндпоин
                        .content(objectMapper.writeValueAsString(student))                                 // преобразовываем                                           // указываем тело
                        .accept(MediaType.APPLICATION_JSON)                                                  // пишем тип данных которые принимает эндпоинт
                        .contentType(MediaType.APPLICATION_JSON))                                           // и возвращает
                .andExpect(status().isOk())                                                           // через andExpect делаем проверки, проверяем что статус 200
                .andExpect(jsonPath("$.name").value("ivan"))                                  // проверить данные
                .andExpect(jsonPath("$.age").value(15));

    }

    @Test
    void update() throws Exception {
        Student student = new Student(1L, "ivan", 15);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(studentRepository.save(ArgumentMatchers.any(Student.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders.put("/student/1")                           // вызываем эндпоин
                        .content(objectMapper.writeValueAsString(student))                                 // преобразовываем                                           // указываем тело
                        .accept(MediaType.APPLICATION_JSON)                                                  // пишем тип данных которые принимает эндпоинт
                        .contentType(MediaType.APPLICATION_JSON))                                           // и возвращает
                .andExpect(status().isOk())                                                           // через andExpect делаем проверки, проверяем что статус 200
                .andExpect(jsonPath("$.name").value("ivan"))                                  // проверить данные
                .andExpect(jsonPath("$.age").value(15));

    }

    @Test
    void delete() throws Exception {
        Student student = new Student(1L, "ivan", 15);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));


        mockMvc.perform(MockMvcRequestBuilders.delete("/student/1")                           // вызываем эндпоин
                        .content(objectMapper.writeValueAsString(student))                                 // преобразовываем                                           // указываем тело
                        .accept(MediaType.APPLICATION_JSON)                                                  // пишем тип данных которые принимает эндпоинт
                        .contentType(MediaType.APPLICATION_JSON))                                           // и возвращает
                .andExpect(status().isOk())                                                           // через andExpect делаем проверки, проверяем что статус 200
                .andExpect(jsonPath("$.name").value("ivan"))                                  // проверить данные
                .andExpect(jsonPath("$.age").value(15));

    }

    @Test
    void filteredByAge() throws Exception {

        when(studentRepository.findAllByAgeBetween(10,20)).thenReturn(Arrays.asList(
                new Student(1L, "ivan", 15),
                new Student(2L, "mihail", 11)
        ));

        mockMvc.perform(MockMvcRequestBuilders.get("/student/by-age?min=10&max=20")                           // вызываем эндпоин
                        .accept(MediaType.APPLICATION_JSON)                                                  // пишем тип данных которые принимает эндпоинт
                        .contentType(MediaType.APPLICATION_JSON))                                           // и возвращает
                .andExpect(status().isOk())                                                           // через andExpect делаем проверки, проверяем что статус 200
                .andExpect(jsonPath("$").isArray())                                  // проверить данные
                .andExpect(jsonPath("$[0].name").value("ivan"))
                .andExpect(jsonPath("$[1].name").value("mihail"));
    }



}
