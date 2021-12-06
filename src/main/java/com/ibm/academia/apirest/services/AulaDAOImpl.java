package com.ibm.academia.apirest.services;

import com.ibm.academia.apirest.entities.Aula;
import com.ibm.academia.apirest.enums.Pizarron;
import com.ibm.academia.apirest.repositories.AulaRepository;
import com.ibm.academia.apirest.repositories.CarreraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AulaDAOImpl  extends GenericoDAOImpl<Aula, AulaRepository> implements AulaDAO{
    @Autowired
    public AulaDAOImpl(AulaRepository repository)
    {
        super(repository);
    }


    @Override
    public Iterable<Aula> findByPizarron(Pizarron pizarron) {
        return repository.findByPizarron(pizarron);
    }

    @Override
    public Iterable<Aula> findByNumeroAula(Integer numero) {
        return repository.findByNumeroAula(numero);
    }

    @Override
    public Iterable<Aula> findByPabellonNombre(String nombre) {
        return repository.findByPabellonNombre(nombre);
    }
}
