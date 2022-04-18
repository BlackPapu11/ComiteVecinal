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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import mx.edu.utez.model.Municipio;
import mx.edu.utez.model.Person;
import mx.edu.utez.model.Role;
import mx.edu.utez.model.User;
import mx.edu.utez.service.MunicipioServiceImpl;
import mx.edu.utez.service.PersonServiceImpl;
import mx.edu.utez.service.RoleServiceImpl;
import mx.edu.utez.service.UserServiceImpl;
import mx.edu.utez.util.ImagenUtileria;

@Controller
@RequestMapping("/administrador")
public class UserController {

	@Autowired
	private UserServiceImpl userService;

	@Autowired
	private MunicipioServiceImpl municipioService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleServiceImpl roleService;

	@GetMapping("/usuario/")
	public String listarUsusarios(User user, Model model, Pageable pageable, Authentication authentication,
			HttpSession session) {
		List<User> allUsers = userService.listar();
		List<User> listaEnlaces = new LinkedList<>();
		List<User> listaPresidente = new LinkedList<>();
		List<User> listaIComite = new LinkedList<>();

		String username = authentication.getName();
		for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
			System.out.println("Role: " + grantedAuthority.getAuthority());
		}

		User user1 = userService.buscarPorCorreo(username);
		// Add data user session
		System.out.println("Nombres: " + user1.getPerson().getNombre());
		session.setAttribute("user", user1.getPerson().getNombre());
		Page<User> listaUsuarios = userService
				.listarPaginacion(PageRequest.of(pageable.getPageNumber(), 5, Sort.by("id").descending()));
		model.addAttribute("listaUsuarios", listaUsuarios);
		return "administrador/listUser";
	}

	@GetMapping("/usuario/eliminar/{id}")
	public String eliminarUsuario(@PathVariable long id, RedirectAttributes redirectAttributes) {
		boolean respuesta = userService.eliminar(id);
		if (respuesta) {
			redirectAttributes.addFlashAttribute("msg_success", "¡Eliminación exitosa!");
		} else {
			redirectAttributes.addFlashAttribute("msg_error", "¡Eliminación fallida!, intenta de nuevo");
		}
		return "redirect:/administrador/municipio/";
	}

	@PostMapping("/usuario/habilitacion")
	public String cambiarStatus(User user, Model model, RedirectAttributes redirectAttributes,
			@RequestParam("userId") String userId) {
		long id = Long.parseLong(userId);
		User userExistente = userService.mostrar(id);
		userExistente.setHabilitado(!userExistente.isHabilitado());
		boolean respuesta = userService.guardar(userExistente);

		if (respuesta) {
			redirectAttributes.addFlashAttribute("msg_success", "¡Actualización exitosa!");
		} else {
			redirectAttributes.addFlashAttribute("msg_error", "¡Ocurrió un error!, intenta de nuevo");
		}

		return "redirect:/administrador/usuario/";
	}

	@GetMapping("/usuario/enlace")
	public String listarEnlaces(User user, Person person, Model model, Pageable pageable, Authentication authentication,
			HttpSession session) {
		List<Role> listRol = roleService.listar();
		List<Role> roleEnlace = new LinkedList<>();
		for(Role role: listRol) {
			if(role.getAuthority().equals("ROL_ENLACE")) {
				roleEnlace.add(role);
			}
		}
		model.addAttribute("listRol",roleEnlace);
		model.addAttribute("listaMunicipios", municipioService.listar());
		String username = authentication.getName();
		for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
			System.out.println("Role: " + grantedAuthority.getAuthority());
		}

		User user1 = userService.buscarPorCorreo(username);
		// Add data user session
		System.out.println("Nombres: " + user1.getPerson().getNombre());
		session.setAttribute("user", user1.getPerson().getNombre());

		List<User> listaUsuarios = userService.listar();
		List<User> listaEnlaces = new LinkedList<>();
		for (User usuario : listaUsuarios) {
			if (usuario.getMunicipio() != null) {
				listaEnlaces.add(usuario);
				System.err.println(usuario.getNumeroEmpleado());
			}
		}

		model.addAttribute("listaEnlaces", listaEnlaces);

		return "administrador/listEnlaces";
	}

	@PostMapping("/usuario/enlace/guardar")
	public String guardarEnlace(@ModelAttribute("user")User user, RedirectAttributes redirectAttributes,
			Model model, Pageable pageable, Authentication authentication, HttpSession session,
			@RequestParam("fotografiaEnlace") MultipartFile multipartFile) {
		
		
		if (user.getId() == null) { // Create
			user.setHabilitado(true);
			user.setContrasena(passwordEncoder.encode(user.getContrasena()));
			// user.setPerson(person);
		} else { // Update
			User userExistente = userService.buscarPorCorreo(user.getCorreo());
			user.setContrasena(userExistente.getContrasena());
			user.setHabilitado(userExistente.isHabilitado());
			if(multipartFile.isEmpty()) {
				user.setFoto(userExistente.getFoto());
			}
		}
		

		if (!multipartFile.isEmpty()) {
			String ruta = "C:/projects/";
			String nombreImagen = ImagenUtileria.guardarImagen(multipartFile, ruta);
			if (nombreImagen != null) {
				user.setFoto(nombreImagen);
			}
		}
		
		boolean respuesta = userService.guardar(user);
		
		if(respuesta) {
			System.err.println(user.getRoles());			
			redirectAttributes.addFlashAttribute("msg_success","¡Se ha realizado el registro correctamente!");
			return "redirect:/administrador/usuario/enlace";
		}else {
			redirectAttributes.addFlashAttribute("msg_error","¡Ocurrió un error, intentalo más tarde!");
			return "redirect:/administrador/usuario/enlace";
		}
		
	}
	
	@PostMapping("/usuario/enlace/habilitacion")
	public String cambiarStatusEnlace(User user, Model model, RedirectAttributes redirectAttributes,
			@RequestParam("userId") String userId) {
		long id = Long.parseLong(userId);
		User userExistente = userService.mostrar(id);
		userExistente.setHabilitado(!userExistente.isHabilitado());
		boolean respuesta = userService.guardar(userExistente);

		if (respuesta) {
			redirectAttributes.addFlashAttribute("msg_success", "¡Actualización exitosa!");
		} else {
			redirectAttributes.addFlashAttribute("msg_error", "¡Ocurrió un error!, intenta de nuevo");
		}

		return "redirect:/administrador/usuario/enlace";
	}

	
	

}
