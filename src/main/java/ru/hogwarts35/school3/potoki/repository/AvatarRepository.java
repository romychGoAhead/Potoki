package ru.hogwarts35.school3.potoki.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hogwarts35.school3.potoki.model.Avatar;
import ru.hogwarts35.school3.potoki.model.Student;

import java.util.Optional;


@Repository
public interface AvatarRepository extends JpaRepository<Avatar, Long> {
    Optional<Avatar> findByStudent(Student student);
}
