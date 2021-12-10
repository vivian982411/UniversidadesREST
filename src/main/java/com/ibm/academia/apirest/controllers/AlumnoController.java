package com.ibm.academia.apirest.controllers;

import com.ibm.academia.apirest.models.entities.Carrera;
import com.ibm.academia.apirest.models.entities.Persona;
import com.ibm.academia.apirest.exceptions.NotFoundException;
import com.ibm.academia.apirest.services.AlumnoDAO;
import com.ibm.academia.apirest.services.CarreraDAO;
import com.ibm.academia.apirest.services.PersonaDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/alumno")
public class AlumnoController {

    Logger logger = LoggerFactory.getLogger(AlumnoController.class);

    @Autowired
    @Qualifier("alumnoDAOImpl")
    private PersonaDAO alumnoDAO;

    @Autowired
    private CarreraDAO carreraDAO;

    /**
     *  Endpoint para crear un objeto Persona de tipo alumno
     * @param alumno
     * @param result
     * @return Objeto de tipo Alumno creado con el codigo http 201
     * @BadRequest cuando los datos no cumplen con las validaciones establecidas
     * @author Vivian Juarez - 06/12/21
     */

    @PostMapping
    public ResponseEntity<?> crearAlumno(@RequestBody Persona alumno, BindingResult result){
        Map<String,Object> validaciones=new HashMap<>();
        if(result.hasErrors()){

            List<String> listaErrores= result.getFieldErrors()
                    .stream()
                    .map(errores -> "Campo: '" +errores.getField() +"' " + errores.getDefaultMessage())
                    .collect(Collectors.toList());
            validaciones.put("Lista Errores",listaErrores);
            return new ResponseEntity<>(listaErrores,HttpStatus.BAD_REQUEST);

        }
        return new ResponseEntity<>( alumnoDAO.guardar(alumno), HttpStatus.CREATED);
    }

    /**
     * Endpoint que lista los alumnos que se tienen registrados.
     * @return Lista de alumnos
     * @NotFoundException Si no encuentra alumnos
     * @author Vivian Juarez - 06/12/21
     */
    @GetMapping("/lista")
    public ResponseEntity<?> obtenerTodos(){
        List<Persona> personas = (List<Persona>) alumnoDAO.buscarTodos();
        if (personas.isEmpty())
            throw new NotFoundException("No existen alumnos");
        return new ResponseEntity<>(personas,HttpStatus.OK);
    }

    /**
     * Endpoint para encontrar a un alumno dado su identificador
     * @param alumnoId identificador de alumno
     * @return Objeto de tipo alumno
     * @NotFoundException Si no encuentra al alumno con el id dado
     * @author Vivian Juarez - 06/12/21
     */
    @GetMapping("/alumnoId/{alumnoId}")
    public ResponseEntity<?> obtenerAlumnoPorId(@PathVariable Integer alumnoId){
        Optional<Persona> optionalPersona = alumnoDAO.buscarPorId(alumnoId);
        if (!optionalPersona.isPresent())
            throw new NotFoundException(String.format("Alumno con id: %d no encontrado",alumnoId));
        return new ResponseEntity<>(optionalPersona.get(),HttpStatus.OK);
    }

    /**
     * Endpoint para actualizar los datos a un alumno dado su identificador
     * @param alumnoId identificador de alumno
     * @param alumno Objeto alumno a encontrar
     * @return Objeto alumno actualizado
     * @NotFoundException Si no encuentra al alumno con el id dado
     * @author Vivian Juarez - 06/12/21
     */
    @PutMapping("/upd/alumnoId/{alumnoId}")
    public ResponseEntity<?> actualizarAlumno(@PathVariable Integer alumnoId, Persona alumno){
        Optional<Persona> oPersona= alumnoDAO.buscarPorId(alumnoId);
        if (!oPersona.isPresent())
            throw new NotFoundException(String.format("Alumno con id: %d no encontrado",alumnoId));
        Persona alumnoActualizado = ((AlumnoDAO)alumnoDAO).actualizar(oPersona.get(),alumno);
        return new ResponseEntity<>(alumnoActualizado,HttpStatus.OK);
    }

    /**
     * Endpoint para eliminar  a un alumno dado su identificador
     * @param alumnoId identificador de alumno
     * @return Codigo 204
     * @NotFoundException Si no encuentra al alumno con el id dado
     * @author Vivian Juarez - 06/12/21
     */
    @DeleteMapping("/alumnoId/{alumnoId}")
    public ResponseEntity<?> eliminarAlumno(@PathVariable Integer alumnoId){
        Optional<Persona> oPersona= alumnoDAO.buscarPorId(alumnoId);
        if (!oPersona.isPresent())
            throw new NotFoundException(String.format("Alumno con id: %d no encontrado",alumnoId));

        alumnoDAO.eliminarPorId(alumnoId);
        return new ResponseEntity<>("Alumno con ID: " + alumnoId + " eliminado satisfactoriamente", HttpStatus.NO_CONTENT);
    }

    /**
     * Endpoint para asignar una carrera a un alumno
     * @param carreraId Identificador de la carrera
     * @param alumnoId Identificador de alumno
     * @return Objeto alumno actualizado
     * @NotFoundException Si no encuentra al alumno o a la carrera con el id dado
     * @author Vivian Juarez - 06/12/21
     */
    @PutMapping("/alumnoId/{alumnoId}/carrera/{carreraId}")
    public ResponseEntity<?> asignarCarreraAlumno(@PathVariable Integer carreraId, @PathVariable Integer alumnoId){
        Optional<Persona> oPersona= alumnoDAO.buscarPorId(alumnoId);
        Optional<Carrera> oCarrera=carreraDAO.buscarPorId(carreraId);
        if (!oPersona.isPresent())
            throw new NotFoundException(String.format("Alumno con id: %d no encontrado",alumnoId));
        if (!oCarrera.isPresent())
            throw new NotFoundException(String.format("Carrera con id: %d no encontrada",carreraId));
        Persona alumno=((AlumnoDAO)alumnoDAO).asociarCarreraAlumno(oPersona.get(),oCarrera.get());
        return new ResponseEntity<>(alumno,HttpStatus.OK);
    }
}
