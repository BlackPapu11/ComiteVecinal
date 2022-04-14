package mx.edu.utez.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import mx.edu.utez.model.Colonia;
import mx.edu.utez.service.ColoniaService;

@Controller
@RequestMapping("/enlace")
public class EnlaceController {
	@Autowired
	ColoniaService coloniaService;

	@GetMapping("/")
	public String homeAdmis(Model model, Authentication authentication, HttpSession session) {
		session.setAttribute("user", authentication.getName());
		session.setAttribute("activeRol", authentication.getAuthorities().iterator().next().getAuthority());
		model.addAttribute("colonia", new Colonia());
		model.addAttribute("editColonia", new Colonia());
		model.addAttribute("colonias", coloniaService.findAll(authentication.getName()));
		return "/enlace/dashboardEnlace";
	}

	@PostMapping("/")
	public RedirectView saveColonia(@ModelAttribute("colonia") Colonia colonia, RedirectAttributes attrs) {
		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl("/enlace/");
		if (coloniaService.saveColonia(colonia)) {
			attrs.addFlashAttribute("msg_success", "Colonia registrada correctamente.");
		} else {
			attrs.addFlashAttribute("msg_error", "Ocurrió un error al registrar la colonia.");
		}
		return redirectView;
	}

	@PostMapping("/editar-colonia")
	public RedirectView updateColonia(@ModelAttribute("editColonia") Colonia colonia, RedirectAttributes attrs) {
		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl("/enlace/");
		if (coloniaService.updateColonia(colonia)) {
			attrs.addFlashAttribute("msg_success", "Colonia modificada correctamente.");
		} else {
			attrs.addFlashAttribute("msg_error", "Ocurrió un error al modificar la colonia.");
		}
		return redirectView;
	}

	@PostMapping("/habilitado")
	public RedirectView saveIncidencia(@RequestParam("colonia") Long id, RedirectAttributes attrs) {
		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl("/enlace/");
		if (coloniaService.statusColonia(id)) {
			attrs.addFlashAttribute("msg_success", "Se ha cambiado el estado de la colonia correctamente.");
		} else {
			attrs.addFlashAttribute("msg_error",
					"Ocurrió un error al cambiar el estado de la colonia.");
		}
		return redirectView;
	}
}
