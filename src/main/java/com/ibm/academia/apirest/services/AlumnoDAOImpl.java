package com.ibm.academia.apirest.services;

import com.ibm.academia.apirest.models.entities.Alumno;
import com.ibm.academia.apirest.models.entities.Carrera;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ibm.academia.apirest.models.entities.Persona;
import com.ibm.academia.apirest.repositories.AlumnoRepository;
import com.ibm.academia.apirest.repositories.PersonaRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AlumnoDAOImpl extends PersonaDAOImpl implements AlumnoDAO {
	@Autowired
	public AlumnoDAOImpl(@Qualifier("repositorioAlumnos")PersonaRepository repository) 
	{
		super(repository);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Persona> buscarAlumnoPorNombreCarrera(String nombre) {
		return ((AlumnoRepository)repository).buscarAlumnoPorNombreCarrera(nombre);
	}
	@Override
	@Transactional
	public Persona actualizar(Persona personaEncontrada, Persona persona) {
		Persona personaActualizada = null;
		personaEncontrada.setNombre(persona.getNombre());
		personaEncontrada.setApellido(persona.getApellido());
		personaEncontrada.setDireccion(persona.getDireccion());
		personaActualizada=repository.save(personaEncontrada);
		return personaActualizada;
	}

	@Override
	@Transactional
	public Persona asociarCarreraAlumno(Persona alumno, Carrera carrera) {
		((Alumno) alumno).setCarrera(carrera);

		return repository.save(alumno);
	}
}