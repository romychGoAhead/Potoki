package ru.hogwarts35.school3.potoki.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts35.school3.potoki.model.Faculty;
import ru.hogwarts35.school3.potoki.service.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("/faculty")
public class FacultyController {

    private final FacultyService facultyService; // подключаем сервис

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping
    public Collection<Faculty> getAll() {
        return facultyService.getAll();
    }
    @GetMapping("/filtered")
    public Collection<Faculty> getByColor (@RequestParam("color") String color){
        return  facultyService.getByColor(color);
    }

    @GetMapping("/{id}")
    public Faculty getById(@PathVariable("id") Long id) {
        return facultyService.getById(id);
    }

    @PostMapping
    public Faculty create(@RequestBody Faculty faculty) {          // @RequestBody-  тело запроса
        return facultyService.create(faculty);
    }

    @PutMapping("/{id}")
    public Faculty update(@PathVariable("id") Long id, @RequestBody Faculty faculty) {
        return facultyService.update(id, faculty);
    }

    @DeleteMapping("/{id}")
    public Faculty remove (@PathVariable Long id) {
        return facultyService.remove(id);
    }

    @GetMapping("/by-search-or-name")
    public Collection<Faculty> getByColorOrName (@RequestParam String search){ // мы передаем параметр
        return  facultyService.getByColorOrName(search,search); // эти параметры мы передаем в оба места и в имя и цвет, т.к парамет поиска у нас и мя и цвет
    }

    @GetMapping("/by-student")
    public Faculty getByStudent(@RequestParam Long studentId){ // передаем студента
        return  facultyService.getByStudentId(studentId);
    }
}
