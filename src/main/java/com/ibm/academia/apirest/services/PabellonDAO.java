package com.ibm.academia.apirest.services;

import com.ibm.academia.apirest.models.entities.Carrera;
import com.ibm.academia.apirest.models.entities.Pabellon;
import com.ibm.academia.apirest.models.entities.Persona;

public interface PabellonDAO extends GenericoDAO<Pabellon>{
    public Iterable<Pabellon> findByDireccionLocalidad(String localidad);
    public Iterable<Pabellon> findByNombre(String nombre);
    public Pabellon actualizar(Pabellon pabellonEncontrado, Pabellon pabellon);
}
