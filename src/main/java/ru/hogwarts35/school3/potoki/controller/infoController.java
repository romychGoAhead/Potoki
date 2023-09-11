package ru.hogwarts35.school3.potoki.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/info")
public class infoController {

    @Value("${server.port:-1}")  // заинжектим его
    private int port;

    @GetMapping("port")
    public int getPort(){
        return port;
    }

}
