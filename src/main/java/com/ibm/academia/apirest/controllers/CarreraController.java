package com.ibm.academia.apirest.controllers;

import com.ibm.academia.apirest.mapper.CarreraMapper;
import com.ibm.academia.apirest.models.dto.CarreraDTO;
import com.ibm.academia.apirest.models.entities.Carrera;
import com.ibm.academia.apirest.exceptions.NotFoundException;
import com.ibm.academia.apirest.services.CarreraDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/carrera")
public class CarreraController {

    Logger logger = LoggerFactory.getLogger(CarreraController.class);

    @Autowired
    private CarreraDAO carreraDAO;

    /**
     * Endpoint para obtener el listado de todas las carreras
     * @return Listado de Carreras encontrado
     * @NotFoudException Cuando el identificador de la carrera no existe
     * @autor Vivian Juarez - 06/12/21
     */
    @GetMapping("/lista/carreras")
    public List<Carrera> buscarTodas(){
        List<Carrera> carreras = (List<Carrera>) carreraDAO.buscarTodos();

        if (carreras.isEmpty()){
            throw new NotFoundException("No existen carreras");
        }
        return carreras;
    }

    /**
     * Endpoint para buscar una carrera por su id
     * @param carreraId Identidicador de carrera
     * @return Objeto carrera con el identificador solicitado
     * @NotFoudException Cuando el identificador de la carrera no existe
     * @autor Vivian Juarez - 06/12/21
     */
    @GetMapping("/id/{carreraId}")
    public Carrera buscarCarreraPorId(@PathVariable(value = "carreraId") Integer carreraId){
        Optional<Carrera> oCarrera = carreraDAO.buscarPorId(carreraId);

        if (!oCarrera.isPresent())
            throw new NotFoundException(String.format("La carrera no existe"));

        return oCarrera.get();
    }

    /**
     * Endpoint para crear una carrera
     * @param carrera Objeto de tipo carrera que se guardara
     * @param result
     * @return Objeto carrera creado en la base de datos
     * @BadRequest Si nos objetos no cumplen con las validaciones
     * @autor Vivian Juarez - 06/12/21
     */
    @PostMapping
    public ResponseEntity<?> guardarCarrera(@Valid @RequestBody Carrera carrera, BindingResult result){
        /*if (carrera.getCantidadAnios()<0)
            throw new BadRequestException("El campo cantidadAnios debe ser mayor a 0");
        if (carrera.getCantidadMaterias()<0)
            throw new BadRequestException("El campo cantidadMaterias debe ser mayor a 0");
        */
        Map<String,Object> validaciones=new HashMap<>();
        if(result.hasErrors()){

            List<String> listaErrores= result.getFieldErrors()
                    .stream()
                    .map(errores -> "Campo: '" +errores.getField() +"' " + errores.getDefaultMessage())
                    .collect(Collectors.toList());
            validaciones.put("Lista Errores",listaErrores);
            return new ResponseEntity<>(listaErrores,HttpStatus.BAD_REQUEST);

        }
        return new ResponseEntity<>( carreraDAO.guardar(carrera), HttpStatus.CREATED);
    }

    /**
     * Endpoint para actualizar un objeto de tipo carrera
     * @param carreraId Parametro para buscar la carrera
     * @param carrera Objeto con la informacion actualizada
     * @return Retorna un objeto de tipo carrera con la informacion actualizada
     * @NotFoundException En caso de que falle al buscar el id de carrera
     * @autor Vivian Juarez - 06/12/21
     */
    @PutMapping("/upd/carreraId/{carreraId}")
    public ResponseEntity actualizarCarrera(@PathVariable Integer carreraId, @RequestBody Carrera carrera){
        Optional<Carrera> optionalCarrera= carreraDAO.buscarPorId(carreraId);
        if (optionalCarrera.isPresent())
            throw new NotFoundException(String.format("La carrera con id %d no existe",carreraId));
        return new ResponseEntity<>(carreraDAO.actualizar(optionalCarrera.get(),carrera),HttpStatus.OK);
    }

    /**
     * Endpoint para eliminar un objeto de tipo carrera
     * @param carreraId Parametro para buscar la carrera a eliminar
     * @return
     * @NotFoundException En caso de que falle al buscar el id de carrera
     * @autor Vivian Juarez - 06/12/21
     */

    @DeleteMapping("/carreraId/{carreraId}")
    public ResponseEntity<?> eliminarCarrera(@PathVariable Integer carreraId){
        Optional<Carrera> optionalCarrera= carreraDAO.buscarPorId(carreraId);
        if (optionalCarrera.isPresent())
            throw new NotFoundException(String.format("La carrera con id %d no existe",carreraId));
        carreraDAO.eliminarPorId(carreraId);
        Map<String,Object> respuesta=new HashMap<>();
        respuesta.put("OK","Carrera eliminada");
        return new ResponseEntity<>(respuesta,HttpStatus.ACCEPTED);
    }

    /**
     * Endpoint para consultar todas las carreras
     * @return retorna una lista de carreras en DTO
     * @NotFoundException En caso de que falle al buscar el id de carrera
     * @autor Vivian Juarez - 06/12/21
     */
    @GetMapping("/carreras/dto")
    public ResponseEntity<?> obtenerCarreraDTO(){
        List<Carrera> carreras= (List<Carrera>) carreraDAO.buscarTodos();
        if (carreras.isEmpty())
            throw new NotFoundException("No existen carreras");
        List<CarreraDTO> listaCarreras = carreras
                .stream()
                .map(CarreraMapper::mapCarrera)
                .collect(Collectors.toList());
        return new ResponseEntity<>(listaCarreras,HttpStatus.OK);
    }
}
