package mx.edu.utez.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import mx.edu.utez.model.Incidencia;
import mx.edu.utez.service.IncidenciaComiteService;

@Controller
@RequestMapping("/incidencia-comite")
public class IncidenciaComiteController {
    @Autowired
    IncidenciaComiteService incidenciaComiteService;

    @RequestMapping(value = "/", method = { RequestMethod.GET, RequestMethod.POST })
    public String index(Authentication authentication, Model model, HttpSession session,
            @RequestParam(name = "comiteId", required = false) Long id,
            @RequestParam(name = "coloniaId", required = false) Long idCol) {
        session.setAttribute("user", authentication.getName());
        session.setAttribute("activeRol", authentication.getAuthorities().iterator().next().getAuthority());
        model.addAttribute("comiteId", id);
        model.addAttribute("coloniaId", idCol);
        model.addAttribute("comites", incidenciaComiteService.findAllComiteByColonia(idCol));
        model.addAttribute("colonias", incidenciaComiteService.findAllColoniaByMunicipio(authentication.getName()));
        model.addAttribute("incidencias", incidenciaComiteService.findByComite(id));
        model.addAttribute("incidencia", new Incidencia());
        return "/enlace/incidencias";
    }

    @GetMapping("/persona-comenta/{id}")
    public ResponseEntity<Object> getPersonComenta(@PathVariable("id") Long id) {
        Map<String, Object> data = new HashMap<>();
        data.put("person", incidenciaComiteService.findUserById(id));
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
