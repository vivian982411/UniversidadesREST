package com.ibm.academia.apirest.command;

import java.util.Optional;

import com.ibm.academia.apirest.entities.Alumno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.ibm.academia.apirest.entities.Carrera;
import com.ibm.academia.apirest.entities.Persona;
import com.ibm.academia.apirest.services.AlumnoDAO;
import com.ibm.academia.apirest.services.CarreraDAO;
import com.ibm.academia.apirest.services.PersonaDAO;

@Component
public class AlumnosComandos implements CommandLineRunner {
    @Autowired
    @Qualifier("alumnoDAOImpl")
    private PersonaDAO personaDao;

    @Autowired
    private CarreraDAO carreraDao;

    @Autowired
    private AlumnoDAO alumnoDao;

    @Override
    public void run(String... args) throws Exception
    {
    	/*Optional<Carrera> ingSistemas = carreraDao.buscarPorId(1);
        System.out.println(ingSistemas);

    	Iterable<Persona> alumnos = personaDao.buscarTodos();
        alumnos.forEach(alumno -> ((Alumno)alumno).setCarrera(ingSistemas.get()));
        alumnos.forEach(alumno -> personaDao.guardar(alumno));

    	Optional<Persona> alumno = personaDao.buscarPorId(2);
        System.out.println(alumno);
    	Optional<Persona> personaNomApe = personaDao.buscarPorNombreYApellido(alumno.get().getNombre(), alumno.get().getApellido());
        System.out.println("APELLIDO + NOMBRE: " + personaNomApe.toString());

    	Optional<Persona> personaDni = personaDao.buscarPorDni(alumno.get().getDni());
        System.out.println("DNI:" + personaDni.toString());

        Iterable<Persona> personasApellido = personaDao.buscarPersonaPorApellido(alumno.get().getApellido());
        personasApellido.forEach(System.out::println);*/

       /* Optional<Carrera> ingSistemas = carreraDao.buscarPorId(1);
        Iterable<Persona> alumnosCarrera = ((AlumnoDAO) personaDao).buscarAlumnoPorNombreCarrera(ingSistemas.get().getNombre());
        alumnosCarrera.forEach(System.out::println);*/

    }
}
