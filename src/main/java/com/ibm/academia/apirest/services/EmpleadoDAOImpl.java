package com.ibm.academia.apirest.services;

import com.ibm.academia.apirest.models.entities.Empleado;
import com.ibm.academia.apirest.enums.TipoEmpleado;
import com.ibm.academia.apirest.models.entities.Pabellon;
import com.ibm.academia.apirest.models.entities.Persona;
import com.ibm.academia.apirest.repositories.EmpleadoRepository;
import com.ibm.academia.apirest.repositories.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmpleadoDAOImpl extends PersonaDAOImpl implements EmpleadoDAO{
    @Autowired
    public EmpleadoDAOImpl(@Qualifier("repositorioEmpleados") PersonaRepository repository)
    {
        super(repository);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Empleado> findEmpleadoByTipoEmpleado(TipoEmpleado tipoEmpleado) {
        return ((EmpleadoRepository)repository).findEmpleadoByTipoEmpleado(tipoEmpleado);
    }

    @Override
    @Transactional
    public Persona asociarPabellonEmpleado(Pabellon pabellon, Persona empleado) {
        ((Empleado)empleado).setPabellon(pabellon);
        return repository.save(empleado);
    }

    @Override
    @Transactional
    public Persona actualizar(Persona empleadoEncontrado, Empleado empleado) {
        Persona empleadoActualizado = null;
        empleadoEncontrado.setNombre(empleado.getNombre());
        empleadoEncontrado.setApellido(empleado.getApellido());
        empleadoEncontrado.setDireccion(empleadoActualizado.getDireccion());
        empleadoActualizado= repository.save(empleadoEncontrado);
        return empleadoActualizado;
    }
}
