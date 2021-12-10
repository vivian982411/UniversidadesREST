package com.ibm.academia.apirest.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ibm.academia.apirest.models.entities.Carrera;

@Repository
public interface CarreraRepository extends CrudRepository<Carrera, Integer> {
	//@Query("select c from Carrera c where c.cantidadAnios =?1")
	public Iterable<Carrera> findCarrerasByCantidadAnios(Integer cantidadAnios);
	
	//@Query("select c from Carrera c where c.nombre  like %?1%")
	public Iterable<Carrera> findCarrerasByNombreContains(String nombre);
	
	//@Query("select c from Carrera c where upper(c.nombre)  like upper(%?1%)")
	public Iterable<Carrera> findCarrerasByNombreContainsIgnoreCase(String nombre);
	
	//@Query("select c from Carrera c where c.cantidadAnios > ?1 ")
	public Iterable<Carrera> findCarrerasByCantidadAniosAfter(Integer cantidadAnios);

	@Query("select c from Carrera c join c.profesores p where p.nombre=?1 and p.apellido=?2")
	public Iterable<Carrera> buscarCarrerasPorProfesorNombreYApellido(String nombre, String apellido);
}