package mx.edu.utez.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import mx.edu.utez.model.Comite;
import mx.edu.utez.service.ColoniaService;
import mx.edu.utez.service.ComiteService;

@Controller
@RequestMapping("/comite")
public class ComiteController {
    @Autowired
    ComiteService comiteService;
    @Autowired
    ColoniaService coloniaService;

    @RequestMapping(value = "/", method = { RequestMethod.GET, RequestMethod.POST })
    public String index(Model model, Authentication authentication, HttpSession session,
            @RequestParam(name = "coloniaId", required = false) Long id) {
        session.setAttribute("user", authentication.getName());
        session.setAttribute("activeRol", authentication.getAuthorities().iterator().next().getAuthority());
        model.addAttribute("comite", new Comite());
        model.addAttribute("editComite", new Comite());
        model.addAttribute("colonias", coloniaService.findAll(authentication.getName()));
        session.setAttribute("coloniaId", id);
        model.addAttribute("comites", comiteService.findAll(id));
        return "/enlace/comite";
    }

    @PostMapping("/save")
    public RedirectView saveComite(@ModelAttribute("comite") Comite comite, RedirectAttributes attrs) {
        RedirectView redirectView = new RedirectView();
        redirectView.setContextRelative(true);
        redirectView.setUrl("/comite/");

        if (comiteService.saveComite(comite)) {
            attrs.addFlashAttribute("msg_success", "Comité registrada correctamente.");
        } else {
            attrs.addFlashAttribute("msg_error",
                    "Ocurrió un error al registrar el comité.");
        }

        return redirectView;
    }

    @PostMapping("/edit")
    public RedirectView updateComite(@ModelAttribute("editComite") Comite comite, RedirectAttributes attrs) {
        RedirectView redirectView = new RedirectView();
        redirectView.setContextRelative(true);
        redirectView.setUrl("/comite/");
        if (comiteService.updateComite(comite)) {
            attrs.addFlashAttribute("msg_success", "Comité modificado correctamente.");
        } else {
            attrs.addFlashAttribute("msg_error",
                    "Ocurrió un error al modificar el comité.");
        }
        return redirectView;
    }

    @PostMapping("/habilitado")
    public RedirectView camibarEstado(@RequestParam("comite") Long id, RedirectAttributes attrs) {
        RedirectView redirectView = new RedirectView();
        redirectView.setContextRelative(true);
        redirectView.setUrl("/comite/");
        if (comiteService.statusComite(id)) {
            attrs.addFlashAttribute("msg_success",
                    "Se ha cambiado el estado de comité correctamente.");
        } else {
            attrs.addFlashAttribute("msg_error",
                    "Ocurrió un error al cambiar el estado del comité.");
        }
        return redirectView;
    }
}
