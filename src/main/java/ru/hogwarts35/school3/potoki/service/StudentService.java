package ru.hogwarts35.school3.potoki.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts35.school3.potoki.exception.StudentNotFoundException;
import ru.hogwarts35.school3.potoki.model.Student;
import ru.hogwarts35.school3.potoki.repository.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Service
public class StudentService {
    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);  //объект с помощью которого мы будем писать loggi в консоли
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // создаем счетчик идентификатора, который будет инкрементироваться при каждом добавлении нового объекта модели в HashMap.

// добавляем CRUD операции

    public Student getById(Long id) {
        logger.info("invoked method getById");   // вызван метод getById
        return studentRepository.findById(id).orElseThrow(StudentNotFoundException::new);

    }

    public Collection<Student> getAll() {
        logger.info("invoked method getAll");
        return studentRepository.findAll();
    }

    public Collection<Student> getByAge(int age) {
        logger.info("invoked method getByAge");
        return studentRepository.findAllByAge(age);

    }   // фильтрация по возрасту

    public Collection<Student> findAllByAgeBetween(int min, int max) {
        logger.info("invoked method findAllByAgeBetween");
        return studentRepository.findAllByAgeBetween(min, max);

    }

    public Student create(Student student) {
        return studentRepository.save(student);
    }

    public Student update(Long id, Student student) {
        logger.info("invoked method update");
        Student exsitingStudent = studentRepository.findById(id).orElseThrow(StudentNotFoundException::new);
        Optional.ofNullable(student.getName()).ifPresent(exsitingStudent::setName);
        Optional.ofNullable(student.getAge()).ifPresent(exsitingStudent::setAge);
        exsitingStudent.setAge(student.getAge());
        return studentRepository.save(exsitingStudent);
    }

    public Student remove(Long id) {
        logger.info("invoked method remove");
        Student Student = studentRepository.findById(id).orElseThrow(StudentNotFoundException::new);
        studentRepository.delete(Student);
        return Student;
    }

    public Long count() {
        logger.info("invoked method count");
        return studentRepository.countStudens();
    }

    public double average() {
        logger.info("invoked method average");
        return studentRepository.averageAge();
    }

    public List<Student> getLastStudent(int quantity) {
        logger.info("invoked method getLastStudent");
        return studentRepository.findLastStudents(quantity);
    }
    //4.6 potoki

    public void printAsync() {

        List<Student> all = studentRepository.findAll();
        System.out.println(all.get(0));
        System.out.println(all.get(1));

        Thread t1 = new Thread(() -> {
            System.out.println(all.get(2));
            System.out.println(all.get(3));

        });
        Thread t2 = new Thread(() -> {
            System.out.println(all.get(4));
            System.out.println(all.get(5));

        });

        t1.start();
        t2.start();

    }

    public void printSync() {

        List<Student> all = studentRepository.findAll();
        printSync(all.get(0));
        printSync(all.get(1));

        Thread t1 = new Thread(() -> {
            printSync(all.get(2));
            printSync(all.get(3));

        });
        Thread t2 = new Thread(() -> {
            printSync(all.get(4));
            printSync(all.get(5));

        });

        t1.start();
        t2.start();

    }

    private synchronized void printSync(Student student) {
        System.out.println(student);

    }

}