package com.ibm.academia.apirest.controllers;

import com.ibm.academia.apirest.exceptions.NotFoundException;

import com.ibm.academia.apirest.models.entities.Persona;
import com.ibm.academia.apirest.models.entities.Profesor;
import com.ibm.academia.apirest.services.PersonaDAO;
import com.ibm.academia.apirest.services.ProfesorDAO;
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
@RequestMapping("/profesor")
public class ProfesorController {
    @Autowired
    @Qualifier("profesorDAOImpl")
    private PersonaDAO profesorDAO;

    /**
     * Endpoint para registrar a un profesor
     * @param profesor objeto profesor para almacenar
     * @param result
     * @return objeto profesor almacenado en la base de datos
     * @BadRequestException Si los datos ingresados no son correctos
     * @author Vivian Juarez - 08/12/21
     */
    @PostMapping
    public ResponseEntity<?> crearProfesor(@RequestBody Profesor profesor, BindingResult result){
        Map<String,Object> validaciones=new HashMap<>();
        if(result.hasErrors()){

            List<String> listaErrores= result.getFieldErrors()
                    .stream()
                    .map(errores -> "Campo: '" +errores.getField() +"' " + errores.getDefaultMessage())
                    .collect(Collectors.toList());
            validaciones.put("Lista Errores",listaErrores);
            return new ResponseEntity<>(listaErrores, HttpStatus.BAD_REQUEST);

        }
        return new ResponseEntity<>( profesorDAO.guardar(profesor), HttpStatus.CREATED);
    }

    /**
     * Enpoint para obtener todos los Profesores registrados
     * @return listado de profesores
     * @NotFoundException Si no encuentra ningun profesor
     * @author Vivian Juarez - 08/12/21
     */
    @GetMapping("/lista")
    public ResponseEntity<?> obtenerTodos(){
        List<Persona> profesores = (List<Persona>) profesorDAO.buscarTodos();
        if (profesores.isEmpty())
            throw new NotFoundException("No existen profesores");
        return new ResponseEntity<>(profesores,HttpStatus.OK);
    }

    /**
     * Endpoint para obtener un profesor dado su identificador
     * @param profesorId identificador del profesor que se busca
     * @return Objeto Profesor encontrado
     * @NotFoundException Si no encuentra al profesor
     * @author Vivian Juarez - 08/12/21
     */
    @GetMapping("/profesorId/{profesorId}")
    public ResponseEntity<?> obtenerProfesorPorId(@PathVariable Integer profesorId){
        Optional<Persona> optionalPersona = profesorDAO.buscarPorId(profesorId);
        if (!optionalPersona.isPresent())
            throw new NotFoundException(String.format("Profesor con id: %d no encontrado",profesorId));
        return new ResponseEntity<>(optionalPersona.get(),HttpStatus.OK);
    }

    /**
     * Endpoint para actualizar los datos de un profesor dado su idenificador
     * @param profesorId identificador del profesor a actualizar
     * @param profesor datos del profesor a actualizar
     * @return Objeto profesor actualizado
     * @NotFoundException Si no encuentra al profesor
     * @author Vivian Juarez - 08/12/21
     */
    @PutMapping("/upd/profesorId/{profesorId}")
    public ResponseEntity<?> actualizarProfesor(@PathVariable Integer profesorId, Profesor profesor){
        Optional<Persona> optionalPersona= profesorDAO.buscarPorId(profesorId);
        if (!optionalPersona.isPresent())
            throw new NotFoundException(String.format("Profesor con id: %d no encontrado",profesorId));
        Persona profesorActualizado = ((ProfesorDAO)profesorDAO).actualizar(optionalPersona.get(),profesor);
        return new ResponseEntity<>(profesorActualizado,HttpStatus.OK);
    }

    /**
     * Endpoint para eliminar a un profesor dado su identificador
     * @param profesorId identificador de profesor
     * @return mensaje de eliminacion correcta
     * @NotFoundException Si no encuentra al profesor
     * @author Vivian Juarez - 08/12/21
     */
    @DeleteMapping("/profesorId/{profesorId}")
    public ResponseEntity<?> eliminarProfesor(@PathVariable Integer profesorId){
        Optional<Persona> optionalPersona= profesorDAO.buscarPorId(profesorId);
        if (!optionalPersona.isPresent())
            throw new NotFoundException(String.format("Profesor con id: %d no encontrado",profesorId));

        profesorDAO.eliminarPorId(profesorId);
        return new ResponseEntity<>("Profesor con ID: " + profesorId + " eliminado.", HttpStatus.NO_CONTENT);
    }

    /**
     * EnpointPara encontrar un listado de profesores que imparten una carrera
     * @param carrera nombre de la carrera para listar los profesores asociados a ella
     * @return Listado de profesores
     * @NotFoundException Si no encuentra ninguna coincidencia
     * @author Vivian Juarez - 08/12/21
     */
    @GetMapping("/findProfesoresByCarrera")
    public ResponseEntity<?> findProfesoresByCarrera(@RequestParam String carrera){
        List<Persona> profesores= (List<Persona>) ((ProfesorDAO)profesorDAO).findProfesoresByCarrera(carrera);
        if (profesores.isEmpty())
            throw new NotFoundException("No se encontraron profesores");
        return new ResponseEntity<>(profesores,HttpStatus.OK);
    }
}
