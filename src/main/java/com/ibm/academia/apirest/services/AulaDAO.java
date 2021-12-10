package com.ibm.academia.apirest.services;

import com.ibm.academia.apirest.models.entities.Aula;
import com.ibm.academia.apirest.enums.Pizarron;
import com.ibm.academia.apirest.models.entities.Pabellon;

public interface AulaDAO extends GenericoDAO<Aula> {
    public Iterable<Aula> findByPizarron(Pizarron pizarron);
    public Iterable<Aula> findByNumeroAula(Integer numero);
    public Iterable<Aula> findByPabellonNombre(String nombre);
    public Aula asociarPabellonAula(Pabellon pabellon, Aula aula);
    public Aula actualizar(Aula aulaEncontrada, Aula aula);
}
