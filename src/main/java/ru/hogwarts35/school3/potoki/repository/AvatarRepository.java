package ru.hogwarts35.school3.potoki.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hogwarts35.school3.potoki.model.Avatar;


@Repository
public interface AvatarRepository extends JpaRepository<Avatar, Long> {
}
