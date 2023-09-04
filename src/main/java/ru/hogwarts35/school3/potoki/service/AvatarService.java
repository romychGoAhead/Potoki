package ru.hogwarts35.school3.potoki.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts35.school3.potoki.dto.AvatarDto;
import ru.hogwarts35.school3.potoki.model.Avatar;
import ru.hogwarts35.school3.potoki.model.Student;
import ru.hogwarts35.school3.potoki.repository.AvatarRepository;
import ru.hogwarts35.school3.potoki.repository.StudentRepository;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.coyote.http11.Constants.a;

@Service

public class AvatarService {

    private AvatarRepository avatarRepository; // инжектим(подключаем)
    private StudentRepository studentRepository;


    @Value("${path.to.avatars.folder}")
    private Path pathToAvatars;
    public AvatarService(AvatarRepository avatarRepository, StudentRepository studentRepository) { // через конструктор
        this.avatarRepository = avatarRepository;
        this.studentRepository = studentRepository;
    }


    public Avatar getById(Long id){
        return avatarRepository.findById(id).orElseThrow();
    }

    public List<AvatarDto> getPage(int pageNum){
        PageRequest pageRequest = PageRequest.of(pageNum, 3);
List<Avatar>avatars = avatarRepository.findAll(pageRequest).getContent();
        return avatars.stream()
                .map(AvatarDto::fromEntity)
                .collect(Collectors.toList());
    }

    // МЕТОД СОХРАНЕНИЯ
    public Long save(Long studentId, MultipartFile multipartFile) throws IOException {   // мультипарт это класс который отвечает за файлы в запросах
        Files.createDirectories(pathToAvatars);                                             // сохраним на диск
        String originalFilename = multipartFile.getOriginalFilename();                 // расширение файла
        int dotIndex = originalFilename.lastIndexOf(".");                          // ищем с конца .
        String extension = originalFilename.substring(dotIndex);                         // получаем расширение
        String fileName = studentId + extension;

        String absolutPath = pathToAvatars.toAbsolutePath() + "/" + fileName;
        FileOutputStream fos = new FileOutputStream(absolutPath);
        multipartFile.getInputStream().transferTo(fos);
        fos.close();


        // сохранить в базу
        Student studentReference = studentRepository.getReferenceById(studentId);
        Avatar avatar = avatarRepository.findByStudent(studentReference)
                .orElse(new Avatar());


        avatar.setStudent(studentReference);  // достать студента по ID
        avatar.setFilePath(absolutPath);                                        // заполняем объект
        avatar.setMediaType(multipartFile.getContentType());                     //
        avatar.setFileSize(multipartFile.getSize());                             //
        avatar.setData(multipartFile.getBytes());                                 //

        avatarRepository.save(avatar);// сохр. объект
        return avatar.getId();
    }



}
