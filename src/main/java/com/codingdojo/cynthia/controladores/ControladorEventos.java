package com.codingdojo.cynthia.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.codingdojo.cynthia.modelos.Estado;
import com.codingdojo.cynthia.modelos.Evento;
import com.codingdojo.cynthia.modelos.Mensaje;
import com.codingdojo.cynthia.modelos.Usuario;
import com.codingdojo.cynthia.servicios.Servicios;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class ControladorEventos {
	
	
	@Autowired
	private Servicios servicios;
	
	
	@GetMapping("/dashboard")
	public String dashboard(HttpSession session,
							Model model, /*Envia variables de controlador al jsp*/
							@ModelAttribute("nuevoEvento") Evento nuevoEvento /*obj vacio form*/ ) {
		/*Revisamos que el usuario haya iniciado sesión*/
		Usuario usuarioTemporal = (Usuario)session.getAttribute("usuarioEnSesion");
		if(usuarioTemporal == null) {
			return "redirect:/";
		}
		
		//Listas de eventos
		
		//Servicio, una función que me regrese la lista de eventos en mi estado
		String miEstado = usuarioTemporal.getEstado(); //Obtenemos el estado del usuario en sesión
		
		List<Evento> eventosMiEstado = servicios.eventosEnMiEstado(miEstado);
		model.addAttribute("eventosMiEstado", eventosMiEstado); //crea una "variable" y la envía al jsp
		
		List<Evento> eventosOtroEstado = servicios.eventosOtroEstado(miEstado);
		model.addAttribute("eventosOtroEstado", eventosOtroEstado);
		
		Usuario miUsuario = servicios.encontrarUsuario(usuarioTemporal.getId());
		model.addAttribute("usuario", miUsuario);
		
		//Enviamos la lista de estados disponible
		model.addAttribute("estados", Estado.Estados);	
		
		return "dashboard.jsp";
	}
	
	@PostMapping("/crear")
	public String crear(HttpSession session,
						@Valid @ModelAttribute("nuevoEvento") Evento nuevoEvento,
						BindingResult result,
						Model model) {
		/*====== REVISAMOS SESION ======*/
		Usuario usuarioTemporal = (Usuario)session.getAttribute("usuarioEnSesion");
		if(usuarioTemporal == null) {
			return "redirect:/";
		}
		/*====== REVISAMOS SESION ======*/
		
		if(result.hasErrors()) {
			model.addAttribute("estados", Estado.Estados);
			String miEstado = usuarioTemporal.getEstado(); //Obtenemos el estado del usuario en sesión
			
			List<Evento> eventosMiEstado = servicios.eventosEnMiEstado(miEstado);
			model.addAttribute("eventosMiEstado", eventosMiEstado); //crea una "variable" y la envía al jsp
			
			List<Evento> eventosOtroEstado = servicios.eventosOtroEstado(miEstado);
			model.addAttribute("eventosOtroEstado", eventosOtroEstado);
			
			Usuario miUsuario = servicios.encontrarUsuario(usuarioTemporal.getId());
			model.addAttribute("usuario", miUsuario);
			return "dashboard.jsp";
		} else {
			servicios.guardarEvento(nuevoEvento);
			return "redirect:/dashboard";
		}
	
	}
	
	@GetMapping("/unir/{id}")
	public String unir(@PathVariable("id") Long eventoId,
					   HttpSession session) {
		/*====== REVISAMOS SESION ======*/
		Usuario usuarioTemporal = (Usuario)session.getAttribute("usuarioEnSesion");
		if(usuarioTemporal == null) {
			return "redirect:/";
		}
		/*====== REVISAMOS SESION ======*/
		
		servicios.unirEvento(usuarioTemporal.getId(), eventoId);
		return "redirect:/dashboard";
	}
	
	@GetMapping("/quitar/{id}")
	public String quitar(@PathVariable("id") Long eventoId,
						 HttpSession session) {
		/*====== REVISAMOS SESION ======*/
		Usuario usuarioTemporal = (Usuario)session.getAttribute("usuarioEnSesion");
		if(usuarioTemporal == null) {
			return "redirect:/";
		}
		/*====== REVISAMOS SESION ======*/
		
		servicios.quitarEvento(usuarioTemporal.getId(), eventoId);
		return "redirect:/dashboard";
	}
	
	@GetMapping("/evento/{id}")
	public String evento(@PathVariable("id") Long eventoId,
						 HttpSession session,
						 Model model,
						 @ModelAttribute("mensaje") Mensaje mensaje) {
		/*====== REVISAMOS SESION ======*/
		Usuario usuarioTemporal = (Usuario)session.getAttribute("usuarioEnSesion");
		if(usuarioTemporal == null) {
			return "redirect:/";
		}
		/*====== REVISAMOS SESION ======*/
		
		Evento evento = servicios.encontrarEvento(eventoId);
		model.addAttribute("evento", evento);
		
		return "evento.jsp";
		
	}
	
	@PostMapping("/crearmensaje")
	public String crearmensaje(@Valid @ModelAttribute("mensaje") Mensaje mensaje,
							   BindingResult result,
							   HttpSession session,
							   Model model) {
		/*====== REVISAMOS SESION ======*/
		Usuario usuarioTemporal = (Usuario)session.getAttribute("usuarioEnSesion");
		if(usuarioTemporal == null) {
			return "redirect:/";
		}
		/*====== REVISAMOS SESION ======*/
		
		if(result.hasErrors()) {
			//Si hay error, tenemos que enviar de nuevo el evento
			//¿cómo se el evento? mi mensaje ya está enlazado a un atributo 
			//evento (gracias al hidden que hicimos)
			model.addAttribute("evento", mensaje.getEvento());
			return "evento.jsp";
		} else {
			servicios.guardarMensaje(mensaje);
			//Regresamos ala misma pantalla, por lo tanto quiero enviar en la url el id del evento
			//Como recibo objeto mensaje, mi objeto mensaje tiene un evento, obtengo el id de este
			return "redirect:/evento/"+mensaje.getEvento().getId();
		}
	}
	
}
