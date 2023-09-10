package ru.hogwarts35.school3.potoki.service;

import org.springframework.stereotype.Service;
import ru.hogwarts35.school3.potoki.exception.StudentNotFoundException;
import ru.hogwarts35.school3.potoki.model.Student;
import ru.hogwarts35.school3.potoki.repository.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // создаем счетчик идентификатора, который будет инкрементироваться при каждом добавлении нового объекта модели в HashMap.

// добавляем CRUD операции

    public Student getById(Long id) {
        return studentRepository.findById(id).orElseThrow(StudentNotFoundException::new);

    }

    public Collection<Student> getAll() {
        return studentRepository.findAll();
    }

    public Collection<Student> getByAge(int age) {
        return studentRepository.findAllByAge(age);

    }   // фильтрация по возрасту

    public Collection<Student> findAllByAgeBetween(int min, int max) {
        return studentRepository.findAllByAgeBetween(min, max);

    }

    public Student create(Student student) {
        return studentRepository.save(student);
    }

    public Student update(Long id, Student student) {
        Student exsitingStudent = studentRepository.findById(id).orElseThrow(StudentNotFoundException::new);
        Optional.ofNullable(student.getName()).ifPresent(exsitingStudent::setName);
        Optional.ofNullable(student.getAge()).ifPresent(exsitingStudent::setAge);
        exsitingStudent.setAge(student.getAge());
        return studentRepository.save(exsitingStudent);
    }

    public Student remove(Long id) {
        Student Student = studentRepository.findById(id).orElseThrow(StudentNotFoundException::new);
        studentRepository.delete(Student);
        return Student;
    }

    public Long count() {
        return studentRepository.countStudens();
    }

    public double average() {
        return studentRepository.averageAge();
    }

    public List<Student> getLastStudent(int quantity) {
        return studentRepository.findLastStudents(quantity);
    }

}