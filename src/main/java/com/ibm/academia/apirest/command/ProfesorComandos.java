package com.ibm.academia.apirest.command;

import com.ibm.academia.apirest.repositories.ProfesorRepository;
import com.ibm.academia.apirest.services.PersonaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ProfesorComandos implements CommandLineRunner {
    @Autowired
    @Qualifier("profesorDAOImpl")
    private PersonaDAO personaDAO;

    @Autowired
    private ProfesorRepository profesorRepository;

    @Override
    public void run(String... args) throws Exception {

        /*Iterable profesoresCarrera = ((ProfesorDAO) personaDAO).findProfesoresByCarrera("Ingenieria en Sistemas");
        profesoresCarrera.forEach(System.out::println);*/


    }

}
