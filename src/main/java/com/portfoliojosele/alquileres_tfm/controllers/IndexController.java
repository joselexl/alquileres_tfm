package com.portfoliojosele.alquileres_tfm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    // Redirige la raíz de la aplicación directamente al listado de contratos
    @GetMapping("/")
    public String index() {
        return "redirect:/contratos/listar";
    }

    // Muestra nuestra página de login personalizada
    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout,
                        Model model) {
        
        // Si Spring nos manda un error (credenciales inválidas)
        if (error != null) {
            model.addAttribute("error", "Usuario o contraseña incorrectos.");
        }
        
        // Si el usuario acaba de cerrar sesión
        if (logout != null) {
            model.addAttribute("success", "Has cerrado sesión correctamente.");
        }
        
        return "login";
    }
}