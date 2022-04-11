package mx.edu.utez.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import mx.edu.utez.model.User;
import mx.edu.utez.service.UserServiceImpl;

@Controller
public class HomeController {
	
	@Autowired
	private UserServiceImpl userServiceImpl;
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@GetMapping("/index")
	public String mostrarIndex(Authentication authentication, HttpSession session) {
		String username = authentication.getName();
		System.out.println("Username: " + username);
		for(GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
			System.out.println("Role: " + grantedAuthority.getAuthority());
		}
		User user = userServiceImpl.buscarPorCorreo(username);
		//Add data user session
		System.out.println("Nombre: " + user.getPerson().getNombre());
		session.setAttribute("user", user.getPerson().getNombre());
		return "redirect:/";
	}
}
