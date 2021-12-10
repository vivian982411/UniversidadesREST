package com.ibm.academia.apirest.command;

import com.ibm.academia.apirest.models.entities.Carrera;
import com.ibm.academia.apirest.services.CarreraDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CarreraComandos implements CommandLineRunner {

    @Autowired
    private CarreraDAO carreraDAO;
    @Override
    public void run(String... args) throws Exception {
        /*Iterable<Carrera> carrerasPorProfesor= carreraDAO.buscarCarrerasPorProfesorNombreYApellido("Jose","juarez");
        carrerasPorProfesor.forEach(System.out::println);*/
    }
}
