package com.ibm.academia.apirest.command;

import com.ibm.academia.apirest.services.AulaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AulaComandos implements CommandLineRunner {

    @Autowired
    private AulaDAO aulaDAO;

    @Override
    public void run(String... args) throws Exception {
        /*Aula aula=new Aula(null,1,"3x3",30, Pizarron.PIZARRA_BLANCA);
        aulaDAO.guardar(aula);*/

        /*Iterable<Aula> aulas = aulaDAO.findByPizarron(Pizarron.PIZARRA_BLANCA);
        aulas.forEach(System.out::println);*/

        /*Aula aula=new Aula(null,2,"3x3",20, Pizarron.PIZARRA_TIZA);
        aulaDAO.guardar(aula);

        Iterable<Aula> aulas = aulaDAO.findByNumeroAula(2);
        aulas.forEach(System.out::println);*/

        /*Iterable<Aula> aulasByPabellon = aulaDAO.findByPabellonNombre("Pabellon3");
        aulasByPabellon.forEach(System.out::println);*/

    }
}
