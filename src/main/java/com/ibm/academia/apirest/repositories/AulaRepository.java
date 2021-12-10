package com.ibm.academia.apirest.repositories;

import com.ibm.academia.apirest.models.entities.Aula;
import com.ibm.academia.apirest.enums.Pizarron;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AulaRepository extends CrudRepository<Aula, Integer> {
    public Iterable<Aula> findByPizarron(Pizarron pizarron);
    public Iterable<Aula> findByNumeroAula(Integer numero);

    @Query("select a from Aula a join fetch a.pabellon p where p.nombre = ?1")
    public Iterable<Aula> findByPabellonNombre(String nombre);
}
