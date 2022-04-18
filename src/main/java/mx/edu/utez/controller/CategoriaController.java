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
import mx.edu.utez.model.User;
import mx.edu.utez.service.CategoriaServiceImpl;
import mx.edu.utez.service.UserServiceImpl;



@Controller
@RequestMapping("/administrador")
public class CategoriaController {
	
	@Autowired
	private CategoriaServiceImpl categoriaService;
	
	@Autowired
	private UserServiceImpl userService;
	
	@GetMapping("/")
	public String listarCategorias(Categoria categoria, Model model, Pageable pageable,Authentication authentication, HttpSession session) {
		String username = authentication.getName();
		session.setAttribute("activeRol", authentication.getAuthorities().iterator().next().getAuthority());
		for(GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
			System.out.println("Role: " + grantedAuthority.getAuthority());
		}
		User user = userService.buscarPorCorreo(username);
		//Add data user session
		System.out.println("Nombres: " + user.getPerson().getNombre());
		session.setAttribute("user", user.getPerson().getNombre());
		Page<Categoria> listaCategoria = categoriaService.listarPaginacion(PageRequest.of(pageable.getPageNumber(), 5, Sort.by("id").descending()));
		   model.addAttribute("listaCategorias", listaCategoria);
		   return "administrador/dashboardAdministrador";
	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminarCategoria(@PathVariable long id, RedirectAttributes redirectAttributes) {
		boolean respuesta = categoriaService.eliminar(id);
		if(respuesta) {
			redirectAttributes.addFlashAttribute("msg_success","¡Eliminación exitosa!");
		}else{
			redirectAttributes.addFlashAttribute("msg_error","¡Eliminación fallida!, intenta de nuevo");
		}
		return "redirect:/administrador/";
	}
	
	@GetMapping("/editar/{id}")
	public String editarCategoria(@PathVariable long id, Model model, RedirectAttributes redirectAttribute) {
		Categoria categoria = categoriaService.mostrar(id);
		if(categoria != null) {
			model.addAttribute("categoria", categoria);
			return null;
		}
		redirectAttribute.addFlashAttribute("msg_error", "Registro no encontrado");
		return "redirect:/administrador/";
	}
	
	@PostMapping("/guardar")
	public String guardarCategoria(Categoria categoria,BindingResult result,RedirectAttributes redirectAttributes,Model model, Pageable pageable,Authentication authentication, HttpSession session) {
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
			Page<Categoria> listaCategoria = categoriaService.listarPaginacion(PageRequest.of(pageable.getPageNumber(), 5, Sort.by("id").descending()));
			model.addAttribute("listaCategorias", listaCategoria);
			return "redirect:/administrador/";
		}
		
		boolean respuesta=categoriaService.guardar(categoria);
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
			Page<Categoria> listaCategoria = categoriaService.listarPaginacion(PageRequest.of(pageable.getPageNumber(), 5, Sort.by("id").descending()));
			   model.addAttribute("listaCategorias", listaCategoria);
			return "redirect:/administrador/";
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
			return "redirect:/administrador/";
		}
		
		
	}
	
}
