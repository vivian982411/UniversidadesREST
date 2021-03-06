package com.ibm.academia.apirest.services;

import com.ibm.academia.apirest.models.entities.*;
import com.ibm.academia.apirest.enums.Pizarron;
import com.ibm.academia.apirest.repositories.AulaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AulaDAOImpl  extends GenericoDAOImpl<Aula, AulaRepository> implements AulaDAO{
    @Autowired
    public AulaDAOImpl(AulaRepository repository)
    {
        super(repository);
    }


    @Override
    @Transactional(readOnly = true)
    public Iterable<Aula> findByPizarron(Pizarron pizarron) {
        return repository.findByPizarron(pizarron);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Aula> findByNumeroAula(Integer numero) {
        return repository.findByNumeroAula(numero);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Aula> findByPabellonNombre(String nombre) {
        return repository.findByPabellonNombre(nombre);
    }

    @Override
    @Transactional
    public Aula actualizar(Aula aulaEncontrada, Aula aula) {
        Aula aulaActualizada = null;
        aulaEncontrada.setMedidas(aula.getMedidas());
        aulaEncontrada.setCantidadPupitres(aula.getCantidadPupitres());
        aulaEncontrada.setPizarron(aula.getPizarron());
        aulaActualizada=repository.save(aulaEncontrada);
        return aulaActualizada;
    }

    @Override
    @Transactional
    public Aula asociarPabellonAula(Pabellon pabellon, Aula aula) {
        aula.setPabellon(pabellon);

        return repository.save(aula);
    }
}
