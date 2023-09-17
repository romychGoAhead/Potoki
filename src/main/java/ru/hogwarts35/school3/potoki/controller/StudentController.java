package ru.hogwarts35.school3.potoki.controller;

import org.springframework.web.bind.annotation.*;

import ru.hogwarts35.school3.potoki.model.Student;
import ru.hogwarts35.school3.potoki.service.StudentService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService; // подключаем сервис

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public Collection<Student> getAll() {
        return studentService.getAll();
    }

    @GetMapping("/{id}")
    public Student getById(@PathVariable("id") Long id) {
        return studentService.getById(id);
    }

    @GetMapping("/filtered")
    public Collection<Student> filtered(@RequestParam int age) {  // фильтр по возрасту
        return studentService.getByAge(age);
    }

    @PostMapping
    public Student create(@RequestBody Student student) {          // @RequestBody-  тело запроса
        return studentService.create(student);
    }

    @PutMapping("/{id}")
    public Student update(@PathVariable("id") Long id, @RequestBody Student student) {
        return studentService.update(id, student);
    }

    @DeleteMapping("/{id}")
    public Student remove(@PathVariable Long id) {
        return studentService.remove(id);
    }

    @GetMapping("/by-age")
    public Collection<Student> filtered(@RequestParam int min, @RequestParam int max) {  // фильтр по min, max
        return studentService.findAllByAgeBetween(min, max);
    }

    @GetMapping("/count")
    public Long count() {
        return studentService.count();
    }

    @GetMapping("/average")
    public double average() {
        return studentService.average();
    }

    @GetMapping("/last-five")
    public List<Student> getLastFive(int quantity) {
        return studentService.getLastStudent(5);
    }

    @GetMapping("/threads/async")
    public void printAsync() {
        studentService.printAsync();

    }
    @GetMapping("/threads/sync")
    public void printSync() {
        studentService.printSync();

    }

}