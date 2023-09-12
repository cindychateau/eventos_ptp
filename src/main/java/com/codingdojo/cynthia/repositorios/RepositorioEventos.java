package com.codingdojo.cynthia.repositorios;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.codingdojo.cynthia.modelos.Evento;

@Repository
public interface RepositorioEventos extends CrudRepository<Evento, Long> {
	
	//SELECT * FROM eventos WHERE estado = <ESTADO RECIBIDO>
	List<Evento> findByEstado(String estado); //Lista de eventos en MI estado
	
	//SELECT * FROM eventos WHERE estado != <ESTADO RECIBIDO>
	List<Evento> findByEstadoIsNot(String estado); 
	
}
