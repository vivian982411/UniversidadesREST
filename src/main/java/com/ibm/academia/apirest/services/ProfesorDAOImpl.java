package com.ibm.academia.apirest.services;

import com.ibm.academia.apirest.models.entities.Persona;
import com.ibm.academia.apirest.models.entities.Profesor;
import com.ibm.academia.apirest.repositories.PersonaRepository;
import com.ibm.academia.apirest.repositories.ProfesorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProfesorDAOImpl extends PersonaDAOImpl implements ProfesorDAO {

    @Autowired
    public ProfesorDAOImpl(@Qualifier("repositorioProfesores") PersonaRepository repository)
    {
        super(repository);
    }


    @Override
    @Transactional(readOnly = true)
    public Iterable<Persona> findProfesoresByCarrera(String carrera) {
        return ((ProfesorRepository)repository).findProfesoresByCarrera(carrera);
    }
    @Override
    @Transactional
    public Persona actualizar(Persona personaEncontrada, Profesor persona) {
        Persona personaActualizada = null;
        personaEncontrada.setNombre(persona.getNombre());
        personaEncontrada.setApellido(persona.getApellido());
        personaEncontrada.setDireccion(persona.getDireccion());
        personaActualizada=repository.save(personaEncontrada);
        return personaActualizada;

    }
}
