package mx.edu.utez.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/enlaces")
public class EnlaceController {
	
	@GetMapping("/index")
	public String homeAdmis() {
		return "administrador/prueba1";
	}
}
