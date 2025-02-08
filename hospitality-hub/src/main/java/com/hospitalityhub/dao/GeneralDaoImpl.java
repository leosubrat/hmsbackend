package com.hospitalityhub.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class GeneralDaoImpl implements GeneralDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public <T> void save(T entity) {
        entityManager.persist(entity);
    }
}
