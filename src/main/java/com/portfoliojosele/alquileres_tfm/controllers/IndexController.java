package com.portfoliojosele.alquileres_tfm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    // Redirijo directamente al listado de contratos
    @GetMapping("/")
    public String index() {
        return "redirect:/contratos/listar";
    }
}