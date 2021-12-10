package com.ibm.academia.apirest.services;

import com.ibm.academia.apirest.datos.DatosDummy;
import com.ibm.academia.apirest.models.entities.Persona;
import com.ibm.academia.apirest.repositories.AlumnoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AlumnoDAOImplTest {

    AlumnoDAO alumnoDAO;
    AlumnoRepository alumnoRepository;

    @BeforeEach
    void setUp() {
        alumnoRepository = mock(AlumnoRepository.class);
        alumnoDAO = new AlumnoDAOImpl(alumnoRepository);
    }
    @Test
    @DisplayName("Test: Buscar alumnos por nombre carrera")
    void buscarAlumnoPorNombreCarrera() {
        //Given
        String carreraNombre = "Ingenieria en Sistemas";
        when(alumnoRepository.buscarAlumnoPorNombreCarrera(carreraNombre)).thenReturn(Arrays.asList(DatosDummy.alumno01(),DatosDummy.alumno02(),DatosDummy.alumno03()));

        //When
        List<Persona> expected =  (List<Persona>) ((AlumnoRepository)alumnoRepository).buscarAlumnoPorNombreCarrera(carreraNombre);

        //Then
        assertThat(expected.size() == 3).isTrue();
    }

    @Test
    @DisplayName("Test: Buscar alumnos por nombre carrera sin valores")
    void buscarAlumnosPorNombreCarreraSinValores() {
        //Given
        String carreraNombre = "Ingenieria en abc";
        when(alumnoRepository.buscarAlumnoPorNombreCarrera(carreraNombre)).thenReturn(Arrays.asList());

        //When
        List<Persona> expected = (List<Persona>) ((AlumnoRepository) alumnoRepository).buscarAlumnoPorNombreCarrera(carreraNombre);

        //Then
        assertThat(expected.isEmpty()).isTrue();
    }
}
