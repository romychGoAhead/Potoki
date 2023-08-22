package ru.hogwarts35.school3.potoki.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts35.school3.potoki.model.Avatar;
import ru.hogwarts35.school3.potoki.service.AvatarService;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
@RequestMapping("/avatar")
public class AvatarController {
    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    // т.к файл передаем в теле запроса
    public ResponseEntity<Long> save(@RequestParam Long studentId,
                                     @RequestBody MultipartFile multipartFile) {

        try {
            Long avatarId = avatarService.save(studentId, multipartFile);
            return ResponseEntity.ok(avatarId);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }


    @GetMapping(value = "/from-disk/{id}")
    public void getFromDisk(@PathVariable("id") Long id, HttpServletResponse response) {
        Avatar avatar = avatarService.getById(id);
        response.setContentType(avatar.getMediaType());
        response.setContentLength((int) avatar.getFileSize());
        try {
            FileInputStream fis = new FileInputStream(avatar.getFilePath());
            fis.transferTo(response.getOutputStream());

            fis.close();
        } catch (IOException e) {
            throw new RuntimeException(e);

        }

    }

    @GetMapping("/from-db/{id}")
    public ResponseEntity<byte[]> getFromDB(@PathVariable("id") Long id) {
        Avatar avatar = avatarService.getById(id);
        byte[] data = avatar.getData();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getFileSize());
        return ResponseEntity.status(200).headers(headers).body(data);
    }

}