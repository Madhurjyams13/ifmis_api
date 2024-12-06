package com.doat.ifmis_api.service;

import com.doat.ifmis_api.model.DdoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;

@Service
public class DdoServiceImpl implements DdoService {

    @Autowired
    private EntityManager em;

    @Override
    public DdoModel getDdoDetails() {

        Query query = em.createQuery("");

        return null;
    }
}
