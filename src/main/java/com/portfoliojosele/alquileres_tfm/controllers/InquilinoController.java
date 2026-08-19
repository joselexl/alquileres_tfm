package com.portfoliojosele.alquileres_tfm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.portfoliojosele.alquileres_tfm.models.entity.Inquilino;
import com.portfoliojosele.alquileres_tfm.models.services.InquilinoService;

import jakarta.validation.Valid;

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

    @GetMapping("/form")
    public String crear(Model model) {       
        Inquilino inquilino = new Inquilino();
        model.addAttribute("inquilino", inquilino);
        model.addAttribute("titulo", "Formulario de Inquilino");
        return "form";
    }

    // método de guardar Inquilino
 @PostMapping("/form")
    public String guardar(@Valid Inquilino inquilino, BindingResult result, Model model, RedirectAttributes flash) {
        
        // errores de validación?
        if (result.hasErrors()) {
            // Si falta el nombre o el DNI  devolvemos el titulo cargamos la pagina del formulario
            model.addAttribute("titulo", "Formulario de Inquilino");
            return "form"; 
        }
        
        // crear o editar
        String mensajeFlash = "";
        if (inquilino.getId() != null && inquilino.getId() > 0) {
            mensajeFlash = "¡Inquilino editado correctamente!";
        } else {
            mensajeFlash = "¡Inquilino creado con éxito!";
        }
        
        // si es correcto permitimos guardar
        inquilinoService.save(inquilino);
        
        // envio mensaje a la vista
        flash.addFlashAttribute("success", mensajeFlash);
        
        return "redirect:/listar";
    }

    @GetMapping("/form/{id}")
    public String editar(@PathVariable(value = "id") Long id, Model model) {        
        Inquilino inquilino = null;
        //  ID sea mayor que 0 
        if (id > 0) {
            // Llamamos a nuestro Service para que busque a Francisco en MySQL
            inquilino = inquilinoService.findOne(id);
        } else {
            // control de id erroneo
            return "redirect:/listar";
        }       
        model.addAttribute("inquilino", inquilino);
        model.addAttribute("titulo", "Editar Inquilino");        
        return "form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
        
        if (id > 0) {
            // Llamamos a tu Service para borrar
            inquilinoService.delete(id);
            // Mandamos mensaje de éxito
            flash.addFlashAttribute("success", "¡Inquilino eliminado con éxito!");
        }
        
        return "redirect:/listar";
    }
}
    
