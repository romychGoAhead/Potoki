package ru.hogwarts35.school3.potoki.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts35.school3.potoki.exception.FacultyNotFoundException;
import ru.hogwarts35.school3.potoki.model.Faculty;
import ru.hogwarts35.school3.potoki.repository.FacultyRepository;


import java.util.Collection;


@Service
public class FacultyService {
    private static final Logger logger= LoggerFactory.getLogger(FacultyService.class);

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) { // заинжектили репозиторий
        this.facultyRepository = facultyRepository;
    }

    public Faculty getById(Long id) {
        logger.info("invoked method getById");
        logger.debug("id = "+id);                     // при отладке будет виден id
        return facultyRepository.findById(id).orElseThrow(FacultyNotFoundException::new);
    }

    public Collection<Faculty> getAll() {   //вернуть всех
        logger.info("invoked method getAll");
        return facultyRepository.findAll();
    }

    public Faculty create(Faculty faculty) {
        logger.info("invoked method create");
        return facultyRepository.save(faculty);
    }

    public Faculty update(Long id, Faculty faculty) {
        logger.info("invoked method update");
        Faculty existingFaculty = facultyRepository
                .findById(id).orElseThrow(FacultyNotFoundException::new);
        if (faculty.getColor() != null) {
            existingFaculty.setColor(faculty.getColor());
        }

        if (faculty.getName() != null) {
            existingFaculty.setName(faculty.getName());
        }

        return facultyRepository.save(existingFaculty); // сохраняем сущ. факультет
    }


    public Faculty remove(long id) {
        logger.info("invoked method remove");
        Faculty faculty = facultyRepository.findById(id)
                .orElseThrow(FacultyNotFoundException::new);
        facultyRepository.delete(faculty);
        return faculty;
    }

    public Collection<Faculty> getByColor(String color) {
        logger.info("invoked method getByColor");
        return facultyRepository.findAllByColor(color);

    }

    public Collection<Faculty> getAllByNameOrColor(String color, String name) {
        logger.info("invoked method getAllByNameOrColor");
        return facultyRepository.findAllByColorIgnoreCaseOrNameIgnoreCase(color, name);
    }
    public Faculty getByStudentId (Long studentId){
        logger.info("invoked method getByStudentId");
        return facultyRepository.findByStudent_id(studentId).orElseThrow(FacultyNotFoundException::new);
    }
}
