package com.ibm.academia.apirest.services;

import com.ibm.academia.apirest.entities.Aula;
import com.ibm.academia.apirest.enums.Pizarron;

public interface AulaDAO extends GenericoDAO<Aula> {
    public Iterable<Aula> findByPizarron(Pizarron pizarron);
    public Iterable<Aula> findByNumeroAula(Integer numero);
    public Iterable<Aula> findByPabellonNombre(String nombre);
}
