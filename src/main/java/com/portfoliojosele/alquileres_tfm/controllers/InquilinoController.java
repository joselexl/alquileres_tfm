package com.portfoliojosele.alquileres_tfm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.portfoliojosele.alquileres_tfm.models.services.InquilinoService;

@Controller
public class InquilinoController {

    private final InquilinoService inquilinoService;

    // Spring inyecta tu Service a través de este constructor
    public InquilinoController(InquilinoService inquilinoService) {
        this.inquilinoService = inquilinoService;
    }

    @GetMapping("/listar")
    public String listar(Model model) {
        model.addAttribute("titulo", "Listado de inquilinos");
        model.addAttribute("inquilinos", inquilinoService.findAll());
        return "listar";
    }
}