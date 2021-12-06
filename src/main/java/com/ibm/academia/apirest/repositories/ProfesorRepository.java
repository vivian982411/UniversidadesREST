package com.ibm.academia.apirest.repositories;

import com.ibm.academia.apirest.entities.Persona;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("repositorioProfesores")
public interface ProfesorRepository extends PersonaRepository {
    @Query("select p from Profesor p join p.carreras c where c.nombre=?1")
    public Iterable<Persona> findProfesoresByCarrera(String carrera);
}