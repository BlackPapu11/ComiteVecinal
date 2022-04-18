package mx.edu.utez.controller;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import mx.edu.utez.model.Comite;
import mx.edu.utez.model.Role;
import mx.edu.utez.model.User;
import mx.edu.utez.service.ColoniaService;
import mx.edu.utez.service.ComiteService;
import mx.edu.utez.service.RoleServiceImpl;
import mx.edu.utez.service.UserServiceImpl;
import mx.edu.utez.util.ImagenUtileria;

@Controller
@RequestMapping("/comite")
public class ComiteController {
    @Autowired
    ComiteService comiteService;
    @Autowired
    ColoniaService coloniaService;
    
    @Autowired
    private UserServiceImpl userService;
    
    @Autowired 
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private RoleServiceImpl roleService;

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
    
    @GetMapping("/integrante")
    public String listarIntegrantes(User user,Model model, Authentication authentication, HttpSession session){
    	List<Role> listRol = roleService.listar();
		List<Role> roleEnlace = new LinkedList<>();
		for(Role role: listRol) {
			if(role.getAuthority().equals("ROL_INTEGRANTE")) {
				roleEnlace.add(role);
			}
		}
		model.addAttribute("listRol",roleEnlace);
    	List<User> users = userService.listar();
    	List<User> listUsers = new LinkedList<>();
    	
    	for(User user1: users) {
    		if(user1.getIdentificacion()!=null) {
    			String nombreImagen = user1.getIdentificacion();
    			String nombreSinEspacios = nombreImagen.replace("-", " ");
    			System.out.println(nombreSinEspacios);
    			user1.setIdentificacion(nombreSinEspacios);
    			listUsers.add(user1);
    		}
    	}
    	model.addAttribute("listaIntegrantes", listUsers);
    	return "enlace/listIntegrantes";
    }
    
    @PostMapping("/integrante/guardar")
	public String guardarEnlace(@ModelAttribute("user")User user, RedirectAttributes redirectAttributes,
			Model model, Pageable pageable, Authentication authentication, HttpSession session,
			@RequestParam("identificacionIntegrante") MultipartFile multipartFile) {

		if (user.getId() == null) { // Create
			user.setHabilitado(true);
			user.setContrasena(passwordEncoder.encode(user.getContrasena()));
			// user.setPerson(person);
		} else { // Update
			User userExistente = userService.buscarPorCorreo(user.getCorreo());
			user.setContrasena(userExistente.getContrasena());
			user.setHabilitado(userExistente.isHabilitado());
			if(multipartFile.isEmpty()) {
				user.setIdentificacion(userExistente.getIdentificacion());
			}
		}
		

		if (!multipartFile.isEmpty()) {
			String ruta = "C:/projects/";
			String nombreImagen = ImagenUtileria.guardarImagen(multipartFile, ruta);
			if (nombreImagen != null) {
				
				String nombreImg = nombreImagen.replace(" ", "-");
				System.err.println(nombreImg);
				
				user.setIdentificacion(nombreImg);
			}
		}
		
		boolean respuesta = userService.guardar(user);
		
		if(respuesta) {
			redirectAttributes.addFlashAttribute("msg_success","¡Se ha realizado el registro correctamente!");
			return "redirect:/comite/integrante";
		}else {
			redirectAttributes.addFlashAttribute("msg_error","¡Ocurrió un error, intentalo más tarde!");
			return "redirect:/comite/integrante";
		}
		
	}
    

	@PostMapping("/integrante/habilitacion")
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

		return "redirect:/comite/integrante";
	}
	
}
