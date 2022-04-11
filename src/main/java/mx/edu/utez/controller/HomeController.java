package mx.edu.utez.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import mx.edu.utez.model.User;
import mx.edu.utez.service.UserServiceImpl;

@Controller
public class HomeController {
	
	@Autowired
	private UserServiceImpl userServiceImpl;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@GetMapping("/login")
	public String mostrarLogin() {
	        return "formLogin";
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request, RedirectAttributes redirectAttributes) {
	       try {
	           SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
	           logoutHandler.logout(request, null, null);
	           redirectAttributes.addFlashAttribute("msg_success", "¡Sesión cerrada! Hasta luego");
	       } catch (Exception e) {
	           redirectAttributes.addFlashAttribute("msg_error", "Ocurrió un error al cerrar la sesión, intenta de nuevo.");
	       }
	       return "redirect:/login";
	}
	
	@GetMapping("/administrador/dashboard")
	public String dashboardAdministrador(Authentication authentication, HttpSession session) {
	       if (session.getAttribute("user") == null) {
	           User user = userServiceImpl.buscarPorCorreo(authentication.getName());
	           user.setContrasena(null);
	           session.setAttribute("user", user.getPerson().getNombre());
	       }
	       return "administrador/dashboardAdministrador";
	}

	@GetMapping("/enlace/dashboard")
	public String dashboardAdoptador(Authentication authentication, HttpSession session) {
	       if (session.getAttribute("user") == null) {
	           User user = userServiceImpl.buscarPorCorreo(authentication.getName());
	           user.setContrasena(null);
	           session.setAttribute("user", user.getPerson().getNombre());
	       }
	       return "enlace/dashboardEnlace";
	}

	@GetMapping("/presidente/dashboard")
	public String dashboardVoluntario(Authentication authentication, HttpSession session) {
	       if (session.getAttribute("user") == null) {
	           User user = userServiceImpl.buscarPorCorreo(authentication.getName());
	           user.setContrasena(null);
	           session.setAttribute("user", user.getPerson().getNombre());
	       }
	       return "presidente/dashboardPresidente";
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
		System.out.println("Nombres: " + user.getPerson().getNombre());
		session.setAttribute("user", user.getPerson().getNombre());
		return "redirect:/";
	}
	
	@GetMapping("/encriptar/{contrasena}")
	@ResponseBody
	public String encriptarContrasenas(@PathVariable("contrasena") String contrasena) {
	       return contrasena + " encriptada con el algoritmo bcrypt: " + passwordEncoder.encode(contrasena);
	}

}
