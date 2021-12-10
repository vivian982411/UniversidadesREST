package com.ibm.academia.apirest.services;

import com.ibm.academia.apirest.models.entities.Aula;
import com.ibm.academia.apirest.models.entities.Empleado;
import com.ibm.academia.apirest.enums.TipoEmpleado;
import com.ibm.academia.apirest.models.entities.Pabellon;
import com.ibm.academia.apirest.models.entities.Persona;

public interface EmpleadoDAO extends PersonaDAO{
    Iterable<Empleado> findEmpleadoByTipoEmpleado(TipoEmpleado tipoEmpleado);
    public Persona asociarPabellonEmpleado(Pabellon pabellon, Persona empleado);
    public Persona actualizar(Persona empleadoEncontrado, Empleado empleado);
}
