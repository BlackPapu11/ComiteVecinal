package mx.edu.utez.controller;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import mx.edu.utez.model.Incidencia;
import mx.edu.utez.service.IncidenciasService;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/president")
public class IncidenciasController {
    private final String DIR = "C:\\projects\\";

    @Autowired
    IncidenciasService service;

    @GetMapping("/")
    public String getAll(Authentication authentication, Model model, HttpSession session) {
        session.setAttribute("user", authentication.getName());
        model.addAttribute("incidencias", service.findAll(authentication.getName()));
        model.addAttribute("categorias", service.findAllCategories());
        model.addAttribute("incidencia", new Incidencia());
        model.addAttribute("editIncidence", new Incidencia());
        return "/presidente/dashboardPresidente";
    }

    @PostMapping(value = "/")
    public RedirectView saveIncidencia(@ModelAttribute("incidencia") Incidencia incidencia,
            @RequestParam("files") MultipartFile[] files, RedirectAttributes attrs, Authentication authentication) {
        RedirectView redirectView = new RedirectView();
        redirectView.setContextRelative(true);
        redirectView.setUrl("/president/");
        if (files.length == 0) {
            attrs.addFlashAttribute("flagRegistering", true);
            attrs.addFlashAttribute("msg_error", "Selecciona un archivo para subir...");
            return redirectView;
        }
        incidencia.setHabilitado(true);
        incidencia.setStatus(1);
        incidencia.setUserComite(service.findUserComite(authentication.getName()).get());
        service.saveIncidence(incidencia, files, DIR);
        attrs.addFlashAttribute("msg_success", "Inicidencia registrada correctamente.");
        return redirectView;
    }

    @PostMapping(value = "/update-incidence")
    public RedirectView updateIncidencia(@ModelAttribute("editIncidence") Incidencia incidencia,
            @RequestParam("edFiles") MultipartFile[] files, RedirectAttributes attrs) {
        RedirectView redirectView = new RedirectView();
        redirectView.setContextRelative(true);
        redirectView.setUrl("/president/");
        Incidencia optional = service.findById(incidencia.getId()).get();
        optional.setNombre(incidencia.getNombre());
        optional.setCategoria(incidencia.getCategoria());
        optional.setDescripcion(incidencia.getDescripcion());
        service.updateIncidence(optional, files, DIR);
        attrs.addFlashAttribute("msg_success", "Inicidencia actualizada correctamente.");
        return redirectView;
    }

    @PostMapping("/habilitado")
    public RedirectView saveIncidencia(@RequestParam("incidencia") Long id, RedirectAttributes attrs) {
        RedirectView redirectView = new RedirectView();
        redirectView.setContextRelative(true);
        redirectView.setUrl("/president/");
        service.statusIncidencia(id);
        attrs.addFlashAttribute("msg_success", "Se ha cambiado el estado de la incidencia correctamente.");
        return redirectView;
    }

    @GetMapping("/list/comxos/{incidencia}")
    public ResponseEntity<Object> getCommentsByIncidencia(@PathVariable("incidencia") long id) {
        return service.findCommentsAndAnexosByIncidencia(id);
    }

    @GetMapping("/remove-anexo/{anexo}")
    public ResponseEntity<Object> removeAnexoById(@PathVariable("anexo") long id) {
        return service.removeAnexoById(id);
    }

}
