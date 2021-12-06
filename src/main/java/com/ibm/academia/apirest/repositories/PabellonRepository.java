package com.ibm.academia.apirest.repositories;

import com.ibm.academia.apirest.entities.Pabellon;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PabellonRepository extends CrudRepository<Pabellon,Integer> {
    public Iterable<Pabellon> findByDireccionLocalidad(String localidad);
    public Iterable<Pabellon> findByNombre(String nombre);
}
