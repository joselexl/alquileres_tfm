package com.portfoliojosele.alquileres_tfm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.portfoliojosele.alquileres_tfm.models.entity.Contrato;
import com.portfoliojosele.alquileres_tfm.models.services.ContratoService;
import com.portfoliojosele.alquileres_tfm.models.services.InquilinoService;
import com.portfoliojosele.alquileres_tfm.models.services.ViviendaService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/contratos")
public class ContratoController {

    private final ContratoService contratoService;
    private final InquilinoService inquilinoService;
    private final ViviendaService viviendaService;

    public ContratoController(ContratoService contratoService, 
                              InquilinoService inquilinoService, 
                              ViviendaService viviendaService) {
        this.contratoService = contratoService;
        this.inquilinoService = inquilinoService;
        this.viviendaService = viviendaService;
    }

    @GetMapping("/listar")
    public String listar(Model model) {
        model.addAttribute("titulo", "Listado de Contratos");
        model.addAttribute("contratos", contratoService.findAll());
        return "contratos/listar";
    }

    @GetMapping("/form")
    public String crear(Model model) {
        Contrato contrato = new Contrato();
        
        model.addAttribute("inquilinos", inquilinoService.findAll());
        model.addAttribute("viviendas", viviendaService.findAll());
        
        model.addAttribute("contrato", contrato);
        model.addAttribute("titulo", "Crear Nuevo Contrato");
        return "contratos/form";
    }

    @PostMapping("/form")
    public String guardar(@Valid Contrato contrato, BindingResult result, Model model, RedirectAttributes flash) {
        
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Formulario de Contrato");
            // Cargo las listas si hay error para que los desplegables no se rompan
            model.addAttribute("inquilinos", inquilinoService.findAll());
            model.addAttribute("viviendas", viviendaService.findAll());
          
            return "contratos/form"; 
        }
        
        String mensajeFlash = "";
        if (contrato.getId() != null && contrato.getId() > 0) {
            mensajeFlash = "¡Contrato editado correctamente!";
        } else {
            mensajeFlash = "¡Contrato creado con éxito!";
        }
        
        contratoService.save(contrato);
        flash.addFlashAttribute("success", mensajeFlash);
        
        // CORREGIDO: Redirect con la ruta completa
        return "redirect:/contratos/listar";
    }

    @GetMapping("/form/{id}")
    public String editar(@PathVariable(value = "id") Long id, Model model) {        
        Contrato contrato = null;
        
        if (id > 0) {
            contrato = contratoService.findOne(id);
        } else {
            
            return "redirect:/contratos/listar";
        }       
        
        // cargo las listas en el modo edición
        model.addAttribute("inquilinos", inquilinoService.findAll());
        model.addAttribute("viviendas", viviendaService.findAll());
        
        model.addAttribute("contrato", contrato);
        model.addAttribute("titulo", "Editar Contrato");        
        
        return "contratos/form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
        if (id > 0) {
            contratoService.delete(id);
            flash.addFlashAttribute("success", "¡Contrato eliminado con éxito!");
        }
        
        return "redirect:/contratos/listar";
    }
}