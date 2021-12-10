package com.ibm.academia.apirest.repositories;

import com.ibm.academia.apirest.models.entities.Empleado;
import com.ibm.academia.apirest.enums.TipoEmpleado;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("repositorioEmpleados")
public interface EmpleadoRepository extends PersonaRepository {
    @Query("select e from empleados e where e.tipoEmpleado = ?1")
    Iterable<Empleado> findEmpleadoByTipoEmpleado(TipoEmpleado tipoEmpleado);
}