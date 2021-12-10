package com.ibm.academia.apirest.repositories;

import com.ibm.academia.apirest.datos.DatosDummy;
import com.ibm.academia.apirest.models.entities.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class ProfesorRepositoryTest {
    @Autowired
    @Qualifier("repositorioProfesores")
    private PersonaRepository personaRepository;

    @Autowired
    private CarreraRepository carreraRepository;

    @BeforeEach
    void setUp(){
        Iterable<Persona> profesores = personaRepository.saveAll(
                Arrays.asList(
                        DatosDummy.profesor01(),
                        DatosDummy.profesor02()
                )
        );
        Iterable<Carrera> carreras = carreraRepository.saveAll(
                Arrays.asList(
                        DatosDummy.carrera01(),
                        DatosDummy.carrera02()
                )
        );
        carreraRepository.saveAll(carreras);
        Set<Carrera> carreraSet= new HashSet<Carrera>(((List<Carrera>)carreras));

        profesores.forEach(profesor -> ((Profesor)profesor).setCarreras(carreraSet));
        personaRepository.saveAll(profesores);
    }

    @AfterEach
    void tearDown(){
        personaRepository.deleteAll();
    }

    @Test
    @DisplayName("Test: Encontrar Profesor por carrera")
    void findByCarrera(){
        Optional<Carrera> carrera=carreraRepository.findById(1);
        Iterable<Persona> expected=((ProfesorRepository)personaRepository).findProfesoresByCarrera(carrera.get().getNombre());
        assertThat(((List<Persona>)expected).size()==1);
    }

}
