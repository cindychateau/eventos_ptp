package com.codingdojo.cynthia.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.codingdojo.cynthia.modelos.Usuario;
import com.codingdojo.cynthia.servicios.Servicios;

import jakarta.servlet.http.HttpSession;

@Controller
public class ControladorEventos {
	
	
	@Autowired
	private Servicios servicios;
	
	
	@GetMapping("/dashboard")
	public String dashboard(HttpSession session) {
		/*Revisamos que el usuario haya iniciado sesión*/
		Usuario usuarioTemporal = (Usuario)session.getAttribute("usuarioEnSesion");
		if(usuarioTemporal == null) {
			return "redirect:/";
		}
		
		return "dashboard.jsp";
	}
}
