package com.portfoliojosele.alquileres_tfm.controllers;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.portfoliojosele.alquileres_tfm.models.services.UsuarioService;

@Controller
@RequestMapping("/usuarios")
// ¡El portero de discoteca! Si intentas entrar aquí y no tienes rol de ADMIN, 
// Spring te corta el rollo y te escupe un error 403 de Acceso Denegado.
@Secured("ROLE_ADMIN") 
public class UsuarioController {

    // Variable 'final' sin @Autowired que se recomienda en estos momentos sin autowired
    private final UsuarioService usuarioService;

    // Inyección por constructor
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping({"", "/"})
    public String listar(Model model) {
        // Preparo el título que le voy a mandar a la vista HTML
        model.addAttribute("titulo", "Gestión de Usuarios");
        
        // Saco la lista completa de la BD y se la enchufo al modelo
        model.addAttribute("usuarios", usuarioService.findAll());
        
        // Devuelvo la ruta del archivo html (sin el .html) que luego pintaremos con Thymeleaf
        return "usuarios/listar"; 
    }
}