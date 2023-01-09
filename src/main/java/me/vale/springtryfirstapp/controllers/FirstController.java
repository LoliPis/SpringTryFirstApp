package me.vale.springtryfirstapp.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirstController {

    @GetMapping
    public String helloWord() {
        return "Приложение запущено";
    }

    @GetMapping("/info")
    public String info() {
        return "Имя ученика: Валерия.\n" +
                "Название проекта: SpringTryFirstApp.\n" +
                "Дата создания проекта: 09.01.2023.\n" +
                "Описание проекта: \n" +
                "Name of  project: SpringTryFirstApp;\n" +
                "Project function: Show two endpoints;\n" +
                "Stack:  Spring-2.7.7;\n" +
                "Language: Java-11.";
    }
}
