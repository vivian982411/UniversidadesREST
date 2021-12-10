package com.ibm.academia.apirest.controllers;

import com.ibm.academia.apirest.exceptions.NotFoundException;
import com.ibm.academia.apirest.models.entities.Persona;
import com.ibm.academia.apirest.services.PersonaDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/persona")
public class PersonaController {

    Logger logger = LoggerFactory.getLogger(PersonaController.class);

    @Autowired
    @Qualifier("alumnoDAOImpl")//Me dice que encuentra 3 beans para la implementacion
    private PersonaDAO personaDAO;

    /**
     * EndPoint para buscar una persona con su nombre y apellido
     * @param nombre Nombre de la persona a buscar
     * @param apellido Apellido de la persona a buscar
     * @return Persona encontrada
     * @NotFoudException Si no se encontro a la persona
     * @author Vivian Juarez 08/12/21
     */
    @GetMapping("/nombre-apellido")
    public ResponseEntity<?> buscarPersonaPorNombreYApellido(@RequestParam String nombre, @RequestParam String apellido){
        Optional<Persona> optionalPersona=personaDAO.buscarPorNombreYApellido(nombre,apellido);
        if(!optionalPersona.isPresent())
            throw new NotFoundException(String.format("No se encontro a la persona con nombre %s y apellido %s",nombre,apellido));
        return new ResponseEntity<>(optionalPersona.get(), HttpStatus.OK);
    }

    /**
     * EndPoint para buscar una persona con su dni
     * @param dni Numero de DNI de la persona que se desea buscar
     * @return Persona encontrada
     * @NotFoudException Si no se encontro a la persona
     * @author Vivian Juarez 08/12/21
     */
    @GetMapping("/dni")
    public ResponseEntity<?> buscarPorDni(@RequestParam String dni){
        Optional<Persona> optionalPersona=personaDAO.buscarPorDni(dni);
        if(!optionalPersona.isPresent())
            throw new NotFoundException(String.format("No se encontro a la persona con DNI %s ",dni));
        return new ResponseEntity<>(optionalPersona.get(), HttpStatus.OK);
    }

    /**
     * Enpoint para encontrar una lista de personas por su apellido
     * @param apellido Apellido de las personas que se desea encontrar
     * @return Lista de Personas encontradas
     * @NotFoudException Si no se encontro ninguna persona
     * @author Vivian Juarez 08/12/21
     */
    @GetMapping("/apellido")
    public ResponseEntity<?> buscarPersonaPorApellido(@RequestParam String apellido){
        List<Persona> personas= (List<Persona>) personaDAO.buscarPersonaPorApellido(apellido);
        if (personas.isEmpty())
            throw new NotFoundException(String.format("No se encontraron personas con el apellido %s ",apellido));
        return new ResponseEntity<>(personas,HttpStatus.OK);
    }

    /**
     * Endpoint para encontrar una persona por su identificador
     * @param id Identificador de la persona
     * @return Persona encontrada
     * @NotFoudException Si no se encontro a la persona
     * @author Vivian Juarez 08/12/21
     */
    public ResponseEntity buscarPorId(@RequestParam Integer id){
        Optional<Persona> optionalPersona= personaDAO.buscarPorId(id);
        if(!optionalPersona.isPresent())
            throw new NotFoundException(String.format("No se encontro a la persona con Identificador %s ",id));
        return new ResponseEntity<>(optionalPersona.get(), HttpStatus.OK);
    }
}
