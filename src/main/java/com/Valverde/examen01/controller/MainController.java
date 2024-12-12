package com.Valverde.examen01.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/infracciones")
    public String infracciones() {
        return "infracciones";
    }
}
