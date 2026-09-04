package com.portfoliojosele.alquileres_tfm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.dao.DataIntegrityViolationException;

import com.portfoliojosele.alquileres_tfm.models.entity.Fianza;
import com.portfoliojosele.alquileres_tfm.models.services.ContratoService;
import com.portfoliojosele.alquileres_tfm.models.services.FianzaService;


import jakarta.validation.Valid;

@Controller
@RequestMapping("/fianzas")
public class FianzaController {

    private final FianzaService fianzaService;
    private final ContratoService contratoService;

    public FianzaController(FianzaService fianzaService, ContratoService contratoService) {
        this.fianzaService = fianzaService;
        this.contratoService = contratoService;
    }

    @GetMapping("/listar")
    public String listar(Model model) {
        model.addAttribute("titulo", "Control de Fianzas");
        model.addAttribute("fianzas", fianzaService.findAll());
        return "fianzas/listar";
    }

    @GetMapping("/form")
    public String crear(Model model) {
        Fianza fianza = new Fianza();
        
        model.addAttribute("contratos", contratoService.findAll());
        model.addAttribute("fianza", fianza);
        model.addAttribute("titulo", "Registrar Nueva Fianza");
        return "fianzas/form";
    }

    @PostMapping("/form")
    public String guardar(@Valid Fianza fianza, BindingResult result, Model model, RedirectAttributes flash) {
        
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Formulario de Fianza");
            model.addAttribute("contratos", contratoService.findAll());
            return "fianzas/form"; 
        }
        
        try {
            // Intentamos guardar en la base de datos
            String mensajeFlash = (fianza.getId() != null && fianza.getId() > 0) ? 
                                  "¡Fianza editada correctamente!" : "¡Fianza registrada con éxito!";
            
            fianzaService.save(fianza);
            flash.addFlashAttribute("success", mensajeFlash);
            
        } catch (DataIntegrityViolationException e) {
            // ¡BINGO! Cazamos el error de MySQL de "unique=true"
            // Le añadimos un error personalizado al campo "contrato"
            result.rejectValue("contrato", "error.contrato", "¡Error! Este contrato ya tiene una fianza asignada. Solo puede haber una.");
            
            // Volvemos a cargar las listas y mostramos el formulario de nuevo
            model.addAttribute("titulo", "Formulario de Fianza");
            model.addAttribute("contratos", contratoService.findAll());
            return "fianzas/form";
        }
        
        return "redirect:/fianzas/listar";
    }

    @GetMapping("/form/{id}")
    public String editar(@PathVariable(value = "id") Long id, Model model) {        
        Fianza fianza = null;
        
        if (id > 0) {
            fianza = fianzaService.findOne(id);
        } else {
            return "redirect:/fianzas/listar";
        }       
        
        // lista de contratos
        model.addAttribute("contratos", contratoService.findAll());
        
        model.addAttribute("fianza", fianza);
        model.addAttribute("titulo", "Editar Fianza");        
        return "fianzas/form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
        if (id > 0) {
            fianzaService.delete(id);
            flash.addFlashAttribute("success", "¡Fianza eliminada con éxito!");
        }
        return "redirect:/fianzas/listar";
    }
}