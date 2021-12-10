package com.ibm.academia.apirest.repositories;

import com.ibm.academia.apirest.datos.DatosDummy;
import com.ibm.academia.apirest.models.entities.Empleado;
import com.ibm.academia.apirest.models.entities.Persona;
import com.ibm.academia.apirest.enums.TipoEmpleado;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class EmpleadoRepositoryTest {

    @Autowired
    @Qualifier("repositorioEmpleados")
    private PersonaRepository personaRepository;

    @BeforeEach
    void setUp(){
        Iterable<Persona> personas = personaRepository.saveAll(
                Arrays.asList(
                        DatosDummy.empleado01(),
                        DatosDummy.empleado02()
                )
        );
        personaRepository.saveAll(personas);
    }

    @AfterEach
    void tearDown(){
        personaRepository.deleteAll();
    }

    @Test
    @DisplayName("Test: Buscar empleado por tipo de empleado")
    void findEmpleadoByTipoEmpleado(){
        List<Empleado> empleadosAdministrativo = (List<Empleado>) ((EmpleadoRepository)personaRepository).findEmpleadoByTipoEmpleado(TipoEmpleado.ADMINISTRATIVO);
        List<Empleado> empleadosMantenimiento = (List<Empleado>) ((EmpleadoRepository)personaRepository).findEmpleadoByTipoEmpleado(TipoEmpleado.MANTENIMIENTO);

        assertThat(empleadosAdministrativo.size()==1);
        assertThat(empleadosMantenimiento.size()==1);
    }
}
