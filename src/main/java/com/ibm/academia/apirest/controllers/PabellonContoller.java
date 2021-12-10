package com.ibm.academia.apirest.controllers;

import com.ibm.academia.apirest.enums.TipoEmpleado;
import com.ibm.academia.apirest.exceptions.NotFoundException;
import com.ibm.academia.apirest.models.entities.Empleado;
import com.ibm.academia.apirest.models.entities.Pabellon;
import com.ibm.academia.apirest.models.entities.Persona;
import com.ibm.academia.apirest.services.EmpleadoDAO;
import com.ibm.academia.apirest.services.PabellonDAO;
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
@RequestMapping("/pabellon")
public class PabellonContoller {
    Logger logger = LoggerFactory.getLogger(PabellonContoller.class);

    @Autowired
    private PabellonDAO pabellonDAO;

    /**
     * Endpoint para crear un pabellon
     * @param pabellon objeto pabellon para almacenar
     * @param result
     * @return Objeto Pabellon almacenado
     * @BadRequestException Si existe algun error con los datos
     * @author Vivian Juarez - 08/12/21
     */
    @PostMapping
    public ResponseEntity<?> crearPabellon(@RequestBody Pabellon pabellon, BindingResult result){
        Map<String,Object> validaciones=new HashMap<>();
        if(result.hasErrors()){

            List<String> listaErrores= result.getFieldErrors()
                    .stream()
                    .map(errores -> "Campo: '" +errores.getField() +"' " + errores.getDefaultMessage())
                    .collect(Collectors.toList());
            validaciones.put("Lista Errores",listaErrores);
            return new ResponseEntity<>(listaErrores, HttpStatus.BAD_REQUEST);

        }
        return new ResponseEntity<>( pabellonDAO.guardar(pabellon), HttpStatus.CREATED);
    }

    /**
     * Endpoint para obtener el listado de los pabellones disponibles
     * @return Listado de objetos de tipo pabellon
     * @NotFoundException Si no encuentra ningun pabellon almacenado
     * @author Vivian Juarez - 08/12/21
     */
    @GetMapping("/lista")
    public ResponseEntity<?> obtenerTodos(){
        List<Pabellon> pabellones = (List<Pabellon>) pabellonDAO.buscarTodos();
        if (pabellones.isEmpty())
            throw new NotFoundException("No existen pabellones");
        return new ResponseEntity<>(pabellones,HttpStatus.OK);
    }

    /**
     * Endpoint para encontrar un pabellon dado su identificador
     * @param pabellonId identificador de pabellon a buscar
     * @return Objeto Pabellon encontrado
     * @NotFoundException Si no encuentra al pabellon con el identificador dado
     * @author Vivian Juarez - 08/12/21
     */
    @GetMapping("/pabellonId/{pabellonId}")
    public ResponseEntity<?> obtenerPabellonPorId(@PathVariable Integer pabellonId){
        Optional<Pabellon> optionalPabellon = pabellonDAO.buscarPorId(pabellonId);
        if (!optionalPabellon.isPresent())
            throw new NotFoundException(String.format("Pabellon con id: %d no encontrado",pabellonId));
        return new ResponseEntity<>(optionalPabellon.get(),HttpStatus.OK);
    }

    /**
     * Enpoint para actualizar la informacion de un pabellon dado su identificador
     * @param pabellonId identificador del pabellon a actualizar
     * @param pabellon informacion del pabellon para actualizar
     * @return Objeto pabellon actualizado
     * @NotFoundException Si no encuentra el pabellon
     * @author Vivian Juarez - 08/12/21
     */
    @PutMapping("/upd/pabellonId/{pabellonId}")
    public ResponseEntity<?> actualizarPabellon(@PathVariable Integer pabellonId, Pabellon pabellon){
        Optional<Pabellon> optionalPabellon= pabellonDAO.buscarPorId(pabellonId);
        if (!optionalPabellon.isPresent())
            throw new NotFoundException(String.format("Pabellon con id: %d no encontrado",pabellonId));
        Pabellon pabellonActualizado = pabellonDAO.actualizar(optionalPabellon.get(),pabellon);
        return new ResponseEntity<>(pabellonActualizado,HttpStatus.OK);
    }

    /**
     * Endpoint para eliminar a un pabellon por su identificador
     * @param pabellonId identificador del pabellon a eliminar
     * @return mensaje de pabellon eliminado
     * @NotFoundException Si no encuentra al pabellon
     * @author Vivian Juarez - 08/12/21
     */
    @DeleteMapping("/pabellonId/{pabellonId}")
    public ResponseEntity<?> eliminarPabellon(@PathVariable Integer pabellonId){
        Optional<Pabellon> optionalPabellon= pabellonDAO.buscarPorId(pabellonId);
        if (!optionalPabellon.isPresent())
            throw new NotFoundException(String.format("Pabellon con id: %d no encontrado",pabellonId));

        pabellonDAO.eliminarPorId(pabellonId);
        return new ResponseEntity<>("Pabellon con ID: " + pabellonId + " eliminado.", HttpStatus.NO_CONTENT);
    }

    /**
     * Endpoint para encontrar un listado de pabellones dado el nombre de la localidad en la que se encuentran
     * @param localidad nombre de la localidad del pabellon a buscar
     * @return Listado de Pabellones que coincidan con el nombre de la localidad
     * @NotFoundException Si no encuentra ninguna coincidencia con los pabellones
     * @author Vivian Juarez - 08/12/21
     */
    @GetMapping("/findByDireccionLocalidad")
    public ResponseEntity<?> findByDireccionLocalidad(@RequestParam String localidad){
        List<Pabellon> pabellones= (List<Pabellon>) pabellonDAO.findByDireccionLocalidad(localidad);
        if (pabellones.isEmpty())
            throw new NotFoundException("No se encontraron pabellones");
        return new ResponseEntity<>(pabellones,HttpStatus.OK);
    }

    /**
     * Endpoint para encontrar un pabellon dado su nombre
     * @param nombre nombre del pabellon a buscar
     * @return listado de pabellones con el nombre dado
     * @NotFoundException Si no encuentra pabellones coincidentes con el nombre
     * @author Vivian Juarez - 08/12/21
     */
    @GetMapping("/findByNombre")
    public ResponseEntity<?> findByNombre(@RequestParam String nombre){
        List<Pabellon> pabellones= (List<Pabellon>) pabellonDAO.findByNombre(nombre);
        if (pabellones.isEmpty())
            throw new NotFoundException("No se encontraron pabellones");
        return new ResponseEntity<>(pabellones,HttpStatus.OK);
    }

}
