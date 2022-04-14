package mx.edu.utez.controller;

import java.util.LinkedList;
import java.util.List;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import mx.edu.utez.model.User;
import mx.edu.utez.service.UserServiceImpl;

@Controller
@RequestMapping("/administrador")
public class UserController {
	
	@Autowired
	private UserServiceImpl userService;
	
	@GetMapping("/usuario/")
	public String listarUsusarios(User user, Model model, Pageable pageable,Authentication authentication, HttpSession session) {
		List<User> allUsers = userService.listar();
		List<User> listaEnlaces = new LinkedList<>();
		List<User> listaPresidente = new LinkedList<>();
		List<User> listaIComite = new LinkedList<>();
		
		String username = authentication.getName();
		for(GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
			System.out.println("Role: " + grantedAuthority.getAuthority());
		}
		
		User user1 = userService.buscarPorCorreo(username);
		//Add data user session
		System.out.println("Nombres: " + user1.getPerson().getNombre());
		session.setAttribute("user", user1.getPerson().getNombre());
		Page<User> listaUsuarios = userService.listarPaginacion(PageRequest.of(pageable.getPageNumber(), 5, Sort.by("id").descending()));
		   model.addAttribute("listaUsuarios", listaUsuarios);
		return "administrador/listUser";
	}
	
	@GetMapping("/usuario/eliminar/{id}")
	public String eliminarUsuario(@PathVariable long id, RedirectAttributes redirectAttributes) {
		boolean respuesta = userService.eliminar(id);
		if(respuesta) {
			redirectAttributes.addFlashAttribute("msg_success","¡Eliminación exitosa!");
		}else{
			redirectAttributes.addFlashAttribute("msg_error","¡Eliminación fallida!, intenta de nuevo");
		}
		return "redirect:/administrador/municipio/";
	}
	
	@PostMapping("/usuario/habilitacion")
	public String cambiarStatus(User user, Model model, RedirectAttributes redirectAttributes, @RequestParam("userId") String userId) {
		long id = Long.parseLong(userId);		
		User userExistente = userService.mostrar(id);
		userExistente.setHabilitado(!userExistente.isHabilitado());
		boolean respuesta = userService.guardar(userExistente);
		
		if(respuesta) {
			redirectAttributes.addFlashAttribute("msg_success","¡Actualización exitosa!");
		}else {
			redirectAttributes.addFlashAttribute("msg_error","¡Ocurrió un error!, intenta de nuevo");
		}
		
		return "redirect:/administrador/usuario/";
	}
	
	@GetMapping("/usuario/enlace")
	public String listarEnlaces(User user, Model model, Pageable pageable,Authentication authentication, HttpSession session) {
		String username = authentication.getName();
		for(GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
			System.out.println("Role: " + grantedAuthority.getAuthority());
		}
		
		User user1 = userService.buscarPorCorreo(username);
		//Add data user session
		System.out.println("Nombres: " + user1.getPerson().getNombre());
		session.setAttribute("user", user1.getPerson().getNombre());
		
		Page<User> listaUsuarios = userService.listarPaginacion(PageRequest.of(pageable.getPageNumber(), 5, Sort.by("id").descending()));
		List<User> listaEnlaces = new LinkedList<>();
		for(User usuario: listaUsuarios) {
			if(usuario.getMunicipio()!=null) {
				listaEnlaces.add(usuario);
				System.err.println(usuario.getNumeroEmpleado());
			}
		}
		
		model.addAttribute("listaEnlaces", listaEnlaces);
		
		
		return "administrador/listEnlaces";
	}
	
}
