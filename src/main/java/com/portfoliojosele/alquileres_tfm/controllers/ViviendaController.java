package com.portfoliojosele.alquileres_tfm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.portfoliojosele.alquileres_tfm.models.entity.Vivienda;
import com.portfoliojosele.alquileres_tfm.models.services.ViviendaService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/viviendas") // para todas las URL de esta clase usará esta
public class ViviendaController {

    private final ViviendaService viviendaService;

    public ViviendaController(ViviendaService viviendaService) {
        this.viviendaService = viviendaService;
    }

    // URL: localhost:8080/viviendas/listar
    @GetMapping("/listar")
    public String listar(Model model) {
        model.addAttribute("titulo", "Listado de viviendas");
        model.addAttribute("viviendas", viviendaService.findAll());
        // Apuntamos a la carpeta "viviendas" dentro de templates
        return "viviendas/listar";
    }

    // URL: localhost:8080/viviendas/form
    @GetMapping("/form")
    public String crear(Model model) {
        Vivienda vivienda = new Vivienda();
        model.addAttribute("vivienda", vivienda);
        model.addAttribute("titulo", "Formulario de Vivienda");
        return "viviendas/form";
    }

    // Método de guardar Vivienda
    @PostMapping("/form")
    public String guardar(@Valid Vivienda vivienda, BindingResult result, Model model, RedirectAttributes flash) {
        
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Formulario de Vivienda");
            return "viviendas/form"; 
        }
        
        String mensajeFlash = "";
        if (vivienda.getId() != null && vivienda.getId() > 0) {
            mensajeFlash = "¡Vivienda editada correctamente!";
        } else {
            mensajeFlash = "¡Vivienda creada con éxito!";
        }
        
        viviendaService.save(vivienda);
        flash.addFlashAttribute("success", mensajeFlash);
        
        return "redirect:/viviendas/listar";
    }

    // Para editar por ID
    @GetMapping("/form/{id}")
    public String editar(@PathVariable(value = "id") Long id, Model model) {        
        Vivienda vivienda = null;
        if (id > 0) {
            vivienda = viviendaService.findOne(id);
        } else {
            return "redirect:/viviendas/listar";
        }       
        model.addAttribute("vivienda", vivienda);
        model.addAttribute("titulo", "Editar Vivienda");        
        return "viviendas/form";
    }

    // Para eliminar por ID
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
        if (id > 0) {
            viviendaService.delete(id);
            flash.addFlashAttribute("success", "¡Vivienda eliminada con éxito!");
        }
        return "redirect:/viviendas/listar";
    }
}