package com.ibm.academia.apirest.command;

import com.ibm.academia.apirest.entities.Direccion;
import com.ibm.academia.apirest.entities.Empleado;
import com.ibm.academia.apirest.entities.Persona;
import com.ibm.academia.apirest.enums.TipoEmpleado;
import com.ibm.academia.apirest.repositories.EmpleadoRepository;
import com.ibm.academia.apirest.services.EmpleadoDAO;
import com.ibm.academia.apirest.services.PersonaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class EmpleadoComandos implements CommandLineRunner {
    @Autowired
    @Qualifier("empleadoDAOImpl")
    private PersonaDAO personaDAO;

    @Autowired
    private EmpleadoRepository empleadoRepository;
    @Override
    public void run(String... args) throws Exception {
        /*Direccion direccion = new Direccion("mirto","21","61514","1","1","zitacuaro");
        Empleado empleado= new Empleado(5,"Hector","Medina","12345",direccion,new BigDecimal(1234.56),TipoEmpleado.ADMINISTRATIVO);
        Empleado empleado1= new Empleado(6,"Dilan","Jaimes","987654",direccion,new BigDecimal(1234.56),TipoEmpleado.MANTENIMIENTO);

        personaDAO.guardar(empleado);
        personaDAO.guardar(empleado1);*/

        /*Iterable<Empleado> empleados=((EmpleadoDAO) personaDAO).findEmpleadoByTipoEmpleado(TipoEmpleado.ADMINISTRATIVO);
        empleados.forEach(System.out::println);*/
    }
}
