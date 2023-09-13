package com.codingdojo.cynthia.servicios;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.codingdojo.cynthia.modelos.Evento;
import com.codingdojo.cynthia.modelos.Mensaje;
import com.codingdojo.cynthia.modelos.Usuario;
import com.codingdojo.cynthia.repositorios.RepositorioEventos;
import com.codingdojo.cynthia.repositorios.RepositorioMensajes;
import com.codingdojo.cynthia.repositorios.RepositorioUsuarios;

@Service
public class Servicios {
	
	@Autowired
	private RepositorioUsuarios ru;
	
	@Autowired
	private RepositorioEventos re;
	
	@Autowired
	private RepositorioMensajes rm;
	
	/* Método que me regitre un nuevo usuario */
	public Usuario registrar(Usuario nuevoUsuario, BindingResult result) {
		//Comparamos contraseñas
		String contrasena = nuevoUsuario.getPassword();
		String confirmacion = nuevoUsuario.getConfirmacion();
		if(!contrasena.equals(confirmacion)) {
			//input, clave, mensaje
			result.rejectValue("confirmacion", "Matches", "Las contraseñas no coinciden");
		}
		
		//Revisamos que el correo que recibimos NO exista en mi BD
		String email = nuevoUsuario.getEmail();
		Usuario existeUsuario = ru.findByEmail(email);
		if(existeUsuario != null) {
			//El correo ya está registrado
			result.rejectValue("email", "Unique", "El correo ingresado ya se encuentra registrado.");
		}
		
		//Si existe error, entonces regresamos null
		if(result.hasErrors()) {
			return null;
		} else {
			//Si NO hay error GUARDAMOS!
			//Encriptamos contraseña
			String contra_encriptada = BCrypt.hashpw(contrasena, BCrypt.gensalt());
			nuevoUsuario.setPassword(contra_encriptada);
			return ru.save(nuevoUsuario);
		}
		
	}
	
	public Usuario login(String email, String password) {
		//Revisamos que el correo que recibimos esté en BD
		Usuario usuarioInicioSesion = ru.findByEmail(email); //Objeto Usuario o null
		//Obj(Usuario) nombre: Elena, apellido: De Troya, password: $2sas9mnasoasla
		if(usuarioInicioSesion == null) {
			return null;
		}
		
		//Comparamos contraseñas
		//BCrypt.checkpw(Contra NO encriptada, Contra SI encriptada) -> 
		//TRUE todo correcto, FALSE si no coinciden
		if(BCrypt.checkpw(password, usuarioInicioSesion.getPassword())) {
			return usuarioInicioSesion;
		}
		
		return null;
		
	}
	
	public Evento guardarEvento(Evento nuevoEvento){
		return re.save(nuevoEvento);
	}
	
	public Usuario encontrarUsuario(Long id) {
		return ru.findById(id).orElse(null);
	}
	
	public List<Evento> eventosEnMiEstado(String estado) {
		return re.findByEstado(estado);
	}
	
	public List<Evento> eventosOtroEstado(String estado) {
		return re.findByEstadoIsNot(estado);
	}
	
	public Evento encontrarEvento(Long id) {
		return re.findById(id).orElse(null);
	}
	
	public void unirEvento(Long usuarioId, Long eventoId) {
		Usuario miUsuario = encontrarUsuario(usuarioId);
		Evento miEvento = encontrarEvento(eventoId);
		
		miUsuario.getEventosAsistidos().add(miEvento);
		ru.save(miUsuario);
		
		/*
		 * miEvento.getAsistentes().add(miUsuario);
		 * re.save(miEvento);
		 */
	}
	
	public void quitarEvento(Long usuarioId, Long eventoId) {
		Usuario miUsuario = encontrarUsuario(usuarioId);
		Evento miEvento = encontrarEvento(eventoId);
		
		miUsuario.getEventosAsistidos().remove(miEvento);
		ru.save(miUsuario);
	}
	
	public Mensaje guardarMensaje(Mensaje nuevoMensaje) {
		return rm.save(nuevoMensaje);
	}
}
