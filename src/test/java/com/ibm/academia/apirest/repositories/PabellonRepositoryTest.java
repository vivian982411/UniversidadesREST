package com.ibm.academia.apirest.repositories;

import com.ibm.academia.apirest.datos.DatosDummy;
import com.ibm.academia.apirest.models.entities.Pabellon;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class PabellonRepositoryTest {

    @Autowired
    private PabellonRepository pabellonRepository;

    @BeforeEach
    void setUp(){
        pabellonRepository.save(DatosDummy.pabellon01());
        pabellonRepository.save(DatosDummy.pabellon02());
    }

    @AfterEach
    void tearDown(){
        pabellonRepository.deleteAll();
    }

    @Test
    @DisplayName("Test: Encontrar Pabellon por localidad")
    void findByDireccionLocalidad(){
        Iterable<Pabellon> expected=pabellonRepository.findByDireccionLocalidad("zitacuaro");
        assertThat(((List<Pabellon>)expected).size()==1);
    }

    @Test
    @DisplayName("Test: Encontrar Pabellon por nombre")
    void findByNombre(){
        Iterable<Pabellon> expected=pabellonRepository.findByNombre("Pabellon2");
        assertThat(((List<Pabellon>)expected).size()==1);
    }
}
