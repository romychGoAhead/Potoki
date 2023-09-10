package ru.hogwarts35.school3.potoki.service;

import org.springframework.stereotype.Service;
import ru.hogwarts35.school3.potoki.exception.FacultyNotFoundException;
import ru.hogwarts35.school3.potoki.model.Faculty;
import ru.hogwarts35.school3.potoki.repository.FacultyRepository;


import java.util.Collection;


@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) { // заинжектили репозиторий
        this.facultyRepository = facultyRepository;
    }

    public Faculty getById(Long id) {

        return facultyRepository.findById(id).orElseThrow(FacultyNotFoundException::new);
    }

    public Collection<Faculty> getAll() {   //вернуть всех
        return facultyRepository.findAll();
    }

    public Faculty create(Faculty faculty) {

        return facultyRepository.save(faculty);
    }

    public Faculty update(Long id, Faculty faculty) {
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
        Faculty faculty = facultyRepository.findById(id)
                .orElseThrow(FacultyNotFoundException::new);
        facultyRepository.delete(faculty);
        return faculty;
    }

    public Collection<Faculty> getByColor(String color) {
        return facultyRepository.findAllByColor(color);

    }

    public Collection<Faculty> getAllByNameOrColor(String color, String name) {
        return facultyRepository.findAllByColorIgnoreCaseOrNameIgnoreCase(color, name);
    }
    public Faculty getByStudentId (Long studentId){
        return facultyRepository.findByStudent_id(studentId).orElseThrow(FacultyNotFoundException::new);
    }
}
