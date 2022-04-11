package mx.edu.utez.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/colonias")
public class ColoniasController {
	
	@GetMapping("/index")
	public String homeEnlace() {
		return "enlace/prueba";
	}
}
