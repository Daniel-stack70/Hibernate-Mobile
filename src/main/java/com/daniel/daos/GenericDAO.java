package com.daniel.daos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class GenericDAO<T> {

    private final Class<T> clazz;
    private final EntityManager em;

    public GenericDAO(Class<T> clazz, EntityManager em) {
        this.clazz = clazz;
        this.em = em;
    }

    // Create
    public void save(T entity) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(entity);
        tx.commit();
    }

    // Read
    public T findById(Integer id) {
        return em.find(clazz, id);
    }

    // Update
    public void update(T entity) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(entity);
        tx.commit();
    }

    // Delete
    public void delete(T entity) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.remove(em.contains(entity) ? entity : em.merge(entity));
        tx.commit();
    }
}
