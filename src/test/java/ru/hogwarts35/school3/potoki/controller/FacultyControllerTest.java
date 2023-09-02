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
import ru.hogwarts35.school3.potoki.service.FacultyService;
import ru.hogwarts35.school3.potoki.service.StudentService;

import java.util.Arrays;
import java.util.Optional;

import static java.util.Arrays.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FacultyController.class) // указываем контроллер который тестируем

public class FacultyControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired                    // в методе create объект faculty преобразовываем в JSON
    ObjectMapper objectMapper;

    @MockBean
    StudentRepository studentRepository;  // мокаем репозтории
    @MockBean
    FacultyRepository facultyRepository;
    @SpyBean
    FacultyService facultyService;   // компоненты которые не надо мокать

    @Test
    void getById() throws Exception {

        Faculty faculty = new Faculty(1L, "math", "red");
        when(facultyRepository.findById(1L)).thenReturn(Optional.of(faculty)); // findById возвращает Optional

        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/1")                           // вызываем эндпоин
                        .accept(MediaType.APPLICATION_JSON)                                                  // пишем тип данных которые принимает эндпоинт
                        .contentType(MediaType.APPLICATION_JSON))                                           // и возвращает
                .andExpect(status().isOk())                                                           // через andExpect делаем проверки, проверяем что статус 200
                .andExpect(jsonPath("$.name").value("math"))                                  // проверить данные
                .andExpect(jsonPath("$.color").value("red"));

    }

    @Test
    void create() throws Exception {
        Faculty faculty = new Faculty(1L, "math", "red");
        when(facultyRepository.save(ArgumentMatchers.any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders.post("/faculty")                           // вызываем эндпоин
                        .content(objectMapper.writeValueAsString(faculty))                                 // преобразовываем                                           // указываем тело
                        .accept(MediaType.APPLICATION_JSON)                                                  // пишем тип данных которые принимает эндпоинт
                        .contentType(MediaType.APPLICATION_JSON))                                           // и возвращает
                .andExpect(status().isOk())                                                           // через andExpect делаем проверки, проверяем что статус 200
                .andExpect(jsonPath("$.name").value("math"))                                  // проверить данные
                .andExpect(jsonPath("$.color").value("red"));

    }

    @Test
    void update() throws Exception {
        Faculty faculty = new Faculty(1L, "math", "red");
        when(facultyRepository.findById(1L)).thenReturn(Optional.of(faculty));
        when(facultyRepository.save(ArgumentMatchers.any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders.put("/faculty/1")                           // вызываем эндпоин
                        .content(objectMapper.writeValueAsString(faculty))                                 // преобразовываем                                           // указываем тело
                        .accept(MediaType.APPLICATION_JSON)                                                  // пишем тип данных которые принимает эндпоинт
                        .contentType(MediaType.APPLICATION_JSON))                                           // и возвращает
                .andExpect(status().isOk())                                                           // через andExpect делаем проверки, проверяем что статус 200
                .andExpect(jsonPath("$.name").value("math"))                                  // проверить данные
                .andExpect(jsonPath("$.color").value("red"));

    }

    @Test
    void delete() throws Exception {
        Faculty faculty = new Faculty(1L, "math", "red");
        when(facultyRepository.findById(1L)).thenReturn(Optional.of(faculty));


        mockMvc.perform(MockMvcRequestBuilders.delete("/faculty/1")                           // вызываем эндпоин
                        .content(objectMapper.writeValueAsString(faculty))                                 // преобразовываем                                           // указываем тело
                        .accept(MediaType.APPLICATION_JSON)                                                  // пишем тип данных которые принимает эндпоинт
                        .contentType(MediaType.APPLICATION_JSON))                                           // и возвращает
                .andExpect(status().isOk())                                                           // через andExpect делаем проверки, проверяем что статус 200
                .andExpect(jsonPath("$.name").value("math"))                                  // проверить данные
                .andExpect(jsonPath("$.color").value("red"));

    }
    @Test
    void filteredByColor() throws Exception {

        when(facultyRepository.findAllByColor("red"))
                .thenReturn(asList(
                        new Faculty(1L, "math", "red"),
                        new Faculty(2L, "fiz", "blu")
                ));

        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/filtered?color=red")                           // вызываем эндпоин
                        .accept(MediaType.APPLICATION_JSON)                                                  // пишем тип данных которые принимает эндпоинт
                        .contentType(MediaType.APPLICATION_JSON))                                           // и возвращает
                .andExpect(status().isOk())                                                           // через andExpect делаем проверки, проверяем что статус 200


                .andExpect(jsonPath("$[0].color").value("red"));


    }
}


