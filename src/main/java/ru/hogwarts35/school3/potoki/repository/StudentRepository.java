package ru.hogwarts35.school3.potoki.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.hogwarts35.school3.potoki.model.Student;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {
    List<Student> findAllByAge(Integer age);
    List<Student> findAllByAgeBetween(int min, int max);
}