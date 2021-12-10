package com.ibm.academia.apirest.controllers;

import com.ibm.academia.apirest.enums.TipoEmpleado;
import com.ibm.academia.apirest.exceptions.NotFoundException;
import com.ibm.academia.apirest.models.entities.*;
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
@RequestMapping("/empleado")
public class EmpleadoController {
    Logger logger = LoggerFactory.getLogger(EmpleadoController.class);

    @Autowired
    @Qualifier("empleadoDAOImpl")
    private PersonaDAO empleadoDAO;

    @Autowired
    private PabellonDAO pabellonDAO;

    /**
     * Endpoint para crear un nuevo empleado
     * @param empleado Objeto empleado para almacenar
     * @param result
     * @return Objeto empleado almacenado
     * @BadRequestException Si existe algun error con los datos proporcionados
     * @author Vivian Juarez - 08/12/21
     */
    @PostMapping
    public ResponseEntity<?> crearEmpleado(@RequestBody Empleado empleado, BindingResult result){
        Map<String,Object> validaciones=new HashMap<>();
        if(result.hasErrors()){

            List<String> listaErrores= result.getFieldErrors()
                    .stream()
                    .map(errores -> "Campo: '" +errores.getField() +"' " + errores.getDefaultMessage())
                    .collect(Collectors.toList());
            validaciones.put("Lista Errores",listaErrores);
            return new ResponseEntity<>(listaErrores, HttpStatus.BAD_REQUEST);

        }
        return new ResponseEntity<>( empleadoDAO.guardar(empleado), HttpStatus.CREATED);
    }

    /**
     * Endpoint para listar a los empleados que se tengan registrados
     * @return lista de empleados
     * @NotFoundException Si no encuentra ningun empleado
     * @author Vivian Juarez - 08/12/21
     */
    @GetMapping("/lista")
    public ResponseEntity<?> obtenerTodos(){
        List<Persona> empleados = (List<Persona>) empleadoDAO.buscarTodos();
        if (empleados.isEmpty())
            throw new NotFoundException("No existen empleados");
        return new ResponseEntity<>(empleados,HttpStatus.OK);
    }

    /**
     * Enpoint para encontrar a un empleado dado su identificador
     * @param empleadoId identificador del empleado
     * @return Objeto empleado encontrado
     * @NotFoundException Si no encuentra al empleado
     * @author Vivian Juarez - 08/12/21
     */
    @GetMapping("/empleadoId/{empleadoId}")
    public ResponseEntity<?> obtenerEmpleadoPorId(@PathVariable Integer empleadoId){
        Optional<Persona> optionalEmpleado = empleadoDAO.buscarPorId(empleadoId);
        if (!optionalEmpleado.isPresent())
            throw new NotFoundException(String.format("Empleado con id: %d no encontrado",empleadoId));
        return new ResponseEntity<>(optionalEmpleado.get(),HttpStatus.OK);
    }

    /**
     * Enpoint para actualizar la informacion de un empleado dado su identificador
     * @param empleadoId identificador del empleado a actualizar
     * @param empleado datos del empleado que se actualizaran
     * @return empleado actualizado
     * @NotFoundException Si no encuentra al empleado
     * @author Vivian Juarez - 08/12/21
     */
    @PutMapping("/upd/empleadoId/{empleadoId}")
    public ResponseEntity<?> actualizarEmpleado(@PathVariable Integer empleadoId, Empleado empleado){
        Optional<Persona> optionalEmpleado= empleadoDAO.buscarPorId(empleadoId);
        if (!optionalEmpleado.isPresent())
            throw new NotFoundException(String.format("Empleado con id: %d no encontrado",empleadoId));
        Persona empleadoActualizado = ((EmpleadoDAO)empleadoDAO).actualizar(optionalEmpleado.get(),empleado);
        return new ResponseEntity<>(empleadoActualizado,HttpStatus.OK);
    }

    /**
     * Endpoint para eliminar un empleado por su identificdor
     * @param empleadoId identificador del empleado a eliminar
     * @return mensaje de empleado eliminado
     * @NotFoundException Si no encuentra al empleado
     * @author Vivian Juarez - 08/12/21
     */
    @DeleteMapping("/empleadoId/{empleadoId}")
    public ResponseEntity<?> eliminarEmpleado(@PathVariable Integer empleadoId){
        Optional<Persona> optionalEmpleado= empleadoDAO.buscarPorId(empleadoId);
        if (!optionalEmpleado.isPresent())
            throw new NotFoundException(String.format("Empleado con id: %d no encontrado",empleadoId));

        empleadoDAO.eliminarPorId(empleadoId);
        return new ResponseEntity<>("Empleado con ID: " + empleadoId + " eliminado.", HttpStatus.NO_CONTENT);
    }

    /**
     * Enpoint para asignar un pabellon a un empleado
     * @param empleadoId identificador de empleado
     * @param pabellonId identificador de pabellon
     * @return Empleado actualizado
     * @NotFoundException Si no encuentra empleado o pabellon con el identificador indicado
     * @author Vivian Juarez - 08/12/21
     */
    @PutMapping("/empleadoId/{empleadoId}/pabellonId/{pabellonId}")
    public ResponseEntity<?> asignarEmpleadoPabellon(@PathVariable Integer empleadoId, @PathVariable Integer pabellonId){
        Optional<Persona> optionalEmpleado= empleadoDAO.buscarPorId(empleadoId);
        Optional<Pabellon> optionalPabellon=pabellonDAO.buscarPorId(pabellonId);
        if (!optionalEmpleado.isPresent())
            throw new NotFoundException(String.format("Empleado con id: %d no encontrado",empleadoId));
        if (!optionalPabellon.isPresent())
            throw new NotFoundException(String.format("Pabellon con id: %d no encontrado",pabellonId));
        Persona empleado=((EmpleadoDAO)empleadoDAO).asociarPabellonEmpleado(optionalPabellon.get(), optionalEmpleado.get());
        return new ResponseEntity<>(empleado,HttpStatus.OK);
    }

    /**
     * Endpoint para encontrar los empleados que coincidan con un tipo de empleado
     * @param tipoEmpleado tipo de empleado para buscar
     * @return Lista de empleados que coincidan con el tipo de empleado
     * @NotFoundException Si no encuentra empleados
     * @author Vivian Juarez - 08/12/21
     */
    @GetMapping("/findEmpleadoByTipoEmpleado")
    public ResponseEntity<?> findEmpleadoByTipoEmpleado(@RequestParam TipoEmpleado tipoEmpleado){
        List<Empleado> empleados= (List<Empleado>) ((EmpleadoDAO)empleadoDAO).findEmpleadoByTipoEmpleado(tipoEmpleado);
        if (empleados.isEmpty())
            throw new NotFoundException("No se encontraron empleados");
        return new ResponseEntity<>(empleados,HttpStatus.OK);
    }
}
