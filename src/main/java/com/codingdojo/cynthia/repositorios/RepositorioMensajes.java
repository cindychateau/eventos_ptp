package com.codingdojo.cynthia.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.codingdojo.cynthia.modelos.Mensaje;

@Repository
public interface RepositorioMensajes extends CrudRepository<Mensaje, Long> {

}
