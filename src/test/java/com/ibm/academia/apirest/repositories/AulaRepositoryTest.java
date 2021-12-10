package com.ibm.academia.apirest.repositories;

import com.ibm.academia.apirest.datos.DatosDummy;
import com.ibm.academia.apirest.models.entities.*;
import com.ibm.academia.apirest.enums.Pizarron;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class AulaRepositoryTest {
    @Autowired
    private AulaRepository aulaRepository;

    @Autowired
    private PabellonRepository pabellonRepository;

    @BeforeEach
    void setUp() {
        Iterable<Aula> aulas = aulaRepository.saveAll(
                Arrays.asList(
                        DatosDummy.aula01(),
                        DatosDummy.aula02(),
                        DatosDummy.aula03())
        );

        Pabellon pabellon = pabellonRepository.save(DatosDummy.pabellon01());
        aulas.forEach(aula -> ((Aula)aula).setPabellon(pabellon));
        aulaRepository.saveAll(aulas);
    }

    @AfterEach
    void tearDown()
    {
        aulaRepository.deleteAll();
    }

    @Test
    @DisplayName("Test: Buscar Aulas por Tipo de Pizarron")
    void findByPizarron(){

        Iterable<Aula> expected = aulaRepository.findByPizarron(Pizarron.PIZARRA_BLANCA);


        assertThat(((List<Aula>)expected).size() == 2).isTrue();

    }

    @Test
    @DisplayName("Test: Buscar Aulas por Numero de Aula")
    void findByNumeroAula(){
        Iterable<Aula> expected = aulaRepository.findByNumeroAula(10);


        assertThat(((List<Aula>)expected).size() == 0).isTrue();
    }

    @Test
    @DisplayName("Test: Buscar Aulas por Nombre de Pabellon")
    void findByPabellonNombre(){
        Iterable<Aula> expected = aulaRepository.findByPabellonNombre("Pabellon1");


        assertThat(((List<Aula>)expected).size() == 3).isTrue();

    }
}
