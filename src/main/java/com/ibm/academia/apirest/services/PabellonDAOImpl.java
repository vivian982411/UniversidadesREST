package com.ibm.academia.apirest.services;

import com.ibm.academia.apirest.entities.Carrera;
import com.ibm.academia.apirest.entities.Pabellon;
import com.ibm.academia.apirest.repositories.CarreraRepository;
import com.ibm.academia.apirest.repositories.PabellonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PabellonDAOImpl extends GenericoDAOImpl<Pabellon, PabellonRepository> implements PabellonDAO {
    @Autowired
    public PabellonDAOImpl(PabellonRepository repository)
    {
        super(repository);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Pabellon> findByDireccionLocalidad(String localidad) {
        return repository.findByDireccionLocalidad(localidad);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Pabellon> findByNombre(String nombre) {
        return repository.findByNombre(nombre);
    }
}
