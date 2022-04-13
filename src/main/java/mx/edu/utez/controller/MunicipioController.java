package mx.edu.utez.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import mx.edu.utez.model.Categoria;
import mx.edu.utez.model.Municipio;
import mx.edu.utez.model.User;
import mx.edu.utez.service.MunicipioServiceImpl;
import mx.edu.utez.service.UserServiceImpl;

@Controller
@RequestMapping("/administrador")
public class MunicipioController {
	
	@Autowired
	private MunicipioServiceImpl municipioService;
	
	@Autowired
	private UserServiceImpl userService;
	
	
	@GetMapping("/municipio/")
	public String listarCategorias(Municipio municipio, Model model, Pageable pageable,Authentication authentication, HttpSession session) {
		String username = authentication.getName();
		for(GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
			System.out.println("Role: " + grantedAuthority.getAuthority());
		}
		User user = userService.buscarPorCorreo(username);
		//Add data user session
		System.out.println("Nombres: " + user.getPerson().getNombre());
		session.setAttribute("user", user.getPerson().getNombre());
		Page<Municipio> listaMunicipio = municipioService.listarPaginacion(PageRequest.of(pageable.getPageNumber(), 5, Sort.by("id").descending()));
		   model.addAttribute("listaMunicipios", listaMunicipio);
		   return "administrador/listMunicipio";
	}
	
	@GetMapping("/municipio/eliminar/{id}")
	public String eliminarCategoria(@PathVariable long id, RedirectAttributes redirectAttributes) {
		boolean respuesta = municipioService.eliminar(id);
		if(respuesta) {
			redirectAttributes.addFlashAttribute("msg_success","¡Eliminación exitosa!");
		}else{
			redirectAttributes.addFlashAttribute("msg_error","¡Eliminación fallida!, intenta de nuevo");
		}
		return "redirect:/administrador/municipio/";
	}
	
	@PostMapping("/municipio/guardar")
	public String guardarCategoria(Municipio municipio,BindingResult result,RedirectAttributes redirectAttributes,Model model, Pageable pageable,Authentication authentication, HttpSession session) {
		if(result.hasErrors()) {
			for(ObjectError error: result.getAllErrors() ) {
				System.out.println("Error:"+error.getDefaultMessage());
			}
			String username = authentication.getName();
			for(GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
				System.out.println("Role: " + grantedAuthority.getAuthority());
			}
			User user = userService.buscarPorCorreo(username);
			//Add data user session
			System.out.println("Nombres: " + user.getPerson().getNombre());
			session.setAttribute("user", user.getPerson().getNombre());
			Page<Municipio> listaMunicipio = municipioService.listarPaginacion(PageRequest.of(pageable.getPageNumber(), 5, Sort.by("id").descending()));
			model.addAttribute("listaCategorias", listaMunicipio);
			return "redirect:/administrador/municipio/";
		}
		
		boolean respuesta=municipioService.guardar(municipio);
		System.out.print("entra"+respuesta);
		if(respuesta) {
			String username = authentication.getName();
			for(GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
				System.out.println("Role: " + grantedAuthority.getAuthority());
			}
			User user = userService.buscarPorCorreo(username);
			//Add data user session
			System.out.println("Nombres: " + user.getPerson().getNombre());
			session.setAttribute("user", user.getPerson().getNombre());
			redirectAttributes.addFlashAttribute("msg_success","¡Registro exitoso!");
			Page<Municipio> listaMunicipio = municipioService.listarPaginacion(PageRequest.of(pageable.getPageNumber(), 5, Sort.by("id").descending()));
			   model.addAttribute("listaCategorias", listaMunicipio);
			return "redirect:/administrador/municipio/";
		}else{
			String username = authentication.getName();
			for(GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
				System.out.println("Role: " + grantedAuthority.getAuthority());
			}
			User user = userService.buscarPorCorreo(username);
			//Add data user session
			System.out.println("Nombres: " + user.getPerson().getNombre());
			session.setAttribute("user", user.getPerson().getNombre());
			redirectAttributes.addFlashAttribute("msg_error","¡Registro fallido!, intenta de nuevo");
			return "redirect:/administrador/municipio/";
		}
		
		
	}
}
