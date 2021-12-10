package com.ibm.academia.apirest.controllers;

import com.ibm.academia.apirest.enums.Pizarron;
import com.ibm.academia.apirest.exceptions.NotFoundException;
import com.ibm.academia.apirest.models.entities.Aula;
import com.ibm.academia.apirest.models.entities.Carrera;
import com.ibm.academia.apirest.models.entities.Pabellon;
import com.ibm.academia.apirest.models.entities.Persona;
import com.ibm.academia.apirest.services.*;
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
@RequestMapping("/aula")
public class AulaController {
    Logger logger = LoggerFactory.getLogger(AulaController.class);

    @Autowired
    private AulaDAO aulaDAO;

    @Autowired
    private PabellonDAO pabellonDAO;

    /**
     * Endpoint para crear un Aula
     * @param aula Objeto Aula para almacenar
     * @param result
     * @return Objeto Aula creado en la base de datos
     * @BadRequest cuando los datos no cumplen con las validaciones establecidas
     * @author Vivian Juarez - 08/12/21
     */
    @PostMapping
    public ResponseEntity<?> crearAula(@RequestBody Aula aula, BindingResult result){
        Map<String,Object> validaciones=new HashMap<>();
        if(result.hasErrors()){

            List<String> listaErrores= result.getFieldErrors()
                    .stream()
                    .map(errores -> "Campo: '" +errores.getField() +"' " + errores.getDefaultMessage())
                    .collect(Collectors.toList());
            validaciones.put("Lista Errores",listaErrores);
            return new ResponseEntity<>(listaErrores, HttpStatus.BAD_REQUEST);

        }
        return new ResponseEntity<>( aulaDAO.guardar(aula), HttpStatus.CREATED);
    }

    /**
     * Endpoint para listar las Aulas que se encuentran registradas
     * @return Lista de Aulas
     * @NotFoundException Si no encuentra aulas
     * @author Vivian Juarez - 08/12/21
     */
    @GetMapping("/lista")
    public ResponseEntity<?> obtenerTodas(){
        List<Aula> aulas = (List<Aula>) aulaDAO.buscarTodos();
        if (aulas.isEmpty())
            throw new NotFoundException("No existen aulas");
        return new ResponseEntity<>(aulas,HttpStatus.OK);
    }

    /**
     * Endpoint para Obtener un Aula por su identificador
     * @param aulaId Identificador del aula
     * @return Objeto Aula encontrado
     * @NotFoundException Si no encuentra aulas
     * @author Vivian Juarez - 08/12/21
     */
    @GetMapping("/aulaId/{aulaId}")
    public ResponseEntity<?> obtenerAulaPorId(@PathVariable Integer aulaId){
        Optional<Aula> optionalAula = aulaDAO.buscarPorId(aulaId);
        if (!optionalAula.isPresent())
            throw new NotFoundException(String.format("Aula con id: %d no encontrada",aulaId));
        return new ResponseEntity<>(optionalAula.get(),HttpStatus.OK);
    }

    /**
     * Endpoint que nos permite actualizar un Aula
     * @param aulaId identificador del aula a actualizar
     * @param aula Objeto aula con propiedades actualizadas
     * @return Objeto aula actualizado
     * @NotFoundException Si no encuentra aulas
     * @author Vivian Juarez - 08/12/21
     */
    @PutMapping("/upd/aulaId/{aulaId}")
    public ResponseEntity<?> actualizarAula(@PathVariable Integer aulaId, Aula aula){
        Optional<Aula> optionalAula= aulaDAO.buscarPorId(aulaId);
        if (!optionalAula.isPresent())
            throw new NotFoundException(String.format("Aula con id: %d no encontrada",aulaId));
        Aula aulaActualizada = aulaDAO.actualizar(optionalAula.get(),aula);
        return new ResponseEntity<>(aulaActualizada,HttpStatus.OK);
    }
    /**
     * Endpoint que nos permite eliminar un Aula
     * @param aulaId identificador del aula a eliminar
     * @return mensaje de eliminacion
     * @NotFoundException Si no encuentra aulas
     * @author Vivian Juarez - 08/12/21
     */
    @DeleteMapping("/aulaId/{aulaId}")
    public ResponseEntity<?> eliminarAula(@PathVariable Integer aulaId){
        Optional<Aula> optionalAula= aulaDAO.buscarPorId(aulaId);
        if (!optionalAula.isPresent())
            throw new NotFoundException(String.format("Aula con id: %d no encontrada",aulaId));

        aulaDAO.eliminarPorId(aulaId);
        return new ResponseEntity<>("Aula con ID: " + aulaId + " eliminada.", HttpStatus.NO_CONTENT);
    }

    /**
     * Endpoint para asignar un pabellon a un aula
     * @param aulaId identificador del aula
     * @param pabellonId identificador del pabellon
     * @return Objeto Aula con el pabellon asignado
     * @NotFoundException Si no encuentra aulas
     * @author Vivian Juarez - 08/12/21
     */
    @PutMapping("/aulaId/{aulaId}/pabellon/{pabellonId}")
    public ResponseEntity<?> asociarPabellonAula(@PathVariable Integer aulaId, @PathVariable Integer pabellonId){
        Optional<Aula> optionalAula= aulaDAO.buscarPorId(aulaId);
        Optional<Pabellon> optionalPabellon=pabellonDAO.buscarPorId(pabellonId);
        if (!optionalAula.isPresent())
            throw new NotFoundException(String.format("Aula con id: %d no encontrada",aulaId));
        if (!optionalPabellon.isPresent())
            throw new NotFoundException(String.format("Pabellon con id: %d no encontrado",pabellonId));
        Aula aula= aulaDAO.asociarPabellonAula(optionalPabellon.get(),optionalAula.get());
        return new ResponseEntity<>(aula,HttpStatus.OK);
    }

    /**
     * Endpoin para listar las aulas con un tipo determinado de pizarron
     * @param pizarron Tipo de pizarron para buscar
     * @return Lista de aulas
     * @NotFoundException Si no encuentra aulas con el pizarron selecionado
     * @author Vivian Juarez - 08/12/21
     */
    @GetMapping("/findByPizarron")
    public ResponseEntity<?> findByPizarron(@RequestParam Pizarron pizarron){
        List<Aula> aulas = (List<Aula>) aulaDAO.findByPizarron(pizarron);
        if (aulas.isEmpty())
            throw new NotFoundException("No se encontraron aulas con el pizarron" + pizarron);
        return new ResponseEntity<>(aulas,HttpStatus.OK);
    }

    /**
     * Endpoint para filtrar las aulas por un numero de aula
     * @param numeroAula numero de aula para buscar
     * @return Aula con el numero indicado
     * @NotFoundException Si no encuentra aulas con el numero indicado
     * @author Vivian Juarez - 08/12/21
     */
    @GetMapping("/findByNumeroAula")
    public ResponseEntity<?> findByNumeroAula(@RequestParam Integer numeroAula){
        List<Aula> aulas = (List<Aula>) aulaDAO.findByNumeroAula(numeroAula);
        if (aulas.isEmpty())
            throw new NotFoundException("No se encontro aula con el numero" + numeroAula);
        return new ResponseEntity<>(aulas,HttpStatus.OK);
    }

    /**
     * Enpoint para listar aulas por nombre del pabellon en el que se encuentren
     * @param nombre nombre del pabellon a buscar
     * @return Lista de aulas que se encuentren en el pabellon
     * @NotFoundException Si no encuentra aulas
     * @author Vivian Juarez - 08/12/21
     */
    @GetMapping("/findByPabellonNombre")
    public ResponseEntity<?> findByPabellonNombre(String nombre){
        List<Aula> aulas = (List<Aula>) aulaDAO.findByPabellonNombre(nombre);
        if (aulas.isEmpty())
            throw new NotFoundException("No se encontraron aulas con el nombre" + nombre);
        return new ResponseEntity<>(aulas,HttpStatus.OK);
    }

}
