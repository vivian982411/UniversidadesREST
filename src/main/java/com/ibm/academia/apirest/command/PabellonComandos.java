package com.ibm.academia.apirest.command;

import com.ibm.academia.apirest.entities.Direccion;
import com.ibm.academia.apirest.entities.Pabellon;
import com.ibm.academia.apirest.repositories.PabellonRepository;
import com.ibm.academia.apirest.services.PabellonDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class PabellonComandos implements CommandLineRunner {

    @Autowired
    private PabellonDAO pabellonDAO;

    @Override
    public void run(String... args) throws Exception {
        /*Direccion direccion = new Direccion("mirto","21","61514","1","1","cdmx");
        Pabellon pabellon=new Pabellon(3,15.0,"Pabellon3",direccion);
        pabellonDAO.guardar(pabellon);*/

        /*Iterable<Pabellon> pabellones= pabellonDAO.buscarTodos();
        pabellones.forEach(System.out::println);*/

        /*Iterable<Pabellon> pabellonZitacuaro=pabellonDAO.findByDireccionLocalidad("cdmx");
        pabellonZitacuaro.forEach(System.out::println);*/

        /*Iterable<Pabellon> pabellonNombre=pabellonDAO.findByNombre("Pabellon1");
        pabellonNombre.forEach(System.out::println);*/
    }
}
