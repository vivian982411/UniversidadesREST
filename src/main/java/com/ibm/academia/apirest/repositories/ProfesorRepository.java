package com.ibm.academia.apirest.repositories;

import com.ibm.academia.apirest.models.entities.Persona;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("repositorioProfesores")
public interface ProfesorRepository extends PersonaRepository {
    @Query("select p from Profesor p join p.carreras c where c.nombre=?1")
    public Iterable<Persona> findProfesoresByCarrera(String carrera);
}