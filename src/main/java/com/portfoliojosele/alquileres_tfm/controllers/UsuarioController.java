package com.portfoliojosele.alquileres_tfm.controllers;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.portfoliojosele.alquileres_tfm.models.entity.Usuario;
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

    // Método para mostrar el formulario vacío para CREAR
    @GetMapping("/form")
    public String crear(Model model) {
        // Le pasamos un usuario totalmente en blanco a la vista
        Usuario usuario = new Usuario();
        
        model.addAttribute("usuario", usuario);
        model.addAttribute("titulo", "Crear Nuevo Usuario");
        
        return "usuarios/form"; 
    }

    // Método que recibe los datos cuando el admin pulsa el botón "Guardar Usuario"
    @PostMapping("/form")
    public String guardar(Usuario usuario) {
        
        // Llamamos al servicio que hicimos antes, que ya se encarga de encriptar la clave
        usuarioService.save(usuario);
        
        // Cuando termine de guardar, hacemos un "redirect" para que vuelva a la lista
        return "redirect:/usuarios"; 
    }

    @GetMapping("/form/{id}")
    public String editar(@PathVariable(value = "id") Long id, Model model) {
        
        Usuario usuario = usuarioService.findById(id);
        
        // Si el admin mete un ID falso en la URL, lo echamos de vuelta a la lista
        if (usuario == null) {
            return "redirect:/usuarios";
        }
        
        usuario.setPassword("");
        
        model.addAttribute("usuario", usuario);
        model.addAttribute("titulo", "Editar Usuario");
        
        return "usuarios/form";
    }

    // Método que atrapa el clic en el botón rojo "Eliminar"
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable(value = "id") Long id) {
        // Le pasamos la guadaña si el ID es mayor que 0
        if (id > 0) {
            usuarioService.delete(id);
        }
        // Y volvemos a cargar la tabla limpia
        return "redirect:/usuarios";
    }
}