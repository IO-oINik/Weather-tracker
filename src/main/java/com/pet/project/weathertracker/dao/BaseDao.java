package com.pet.project.weathertracker.dao;

import com.pet.project.weathertracker.exceptions.EntityExistException;
import com.pet.project.weathertracker.utils.HibernateSessionFactoryUtil;
import jakarta.persistence.PersistenceException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Optional;

public abstract class BaseDao<E, K> {
    private final Class<E> clazz;

    public BaseDao(Class<E> clazz) {
        this.clazz = clazz;
    }
    public Optional<E> getById(K id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Optional<E> entity = Optional.ofNullable(session.get(clazz, id));
        session.close();
        return entity;
    }

    public void save(E entity) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            session.persist(entity);
            tx.commit();
        } catch (PersistenceException e) {
            tx.rollback();
            throw new EntityExistException("Entity " + entity.toString() + " already exists");
        } finally {
            session.close();
        }
    }

    public void delete(E entity) {
        HibernateSessionFactoryUtil.getSessionFactory().inTransaction(session -> {
            session.remove(entity);
        });
    }

    public void update(E entity) {
        HibernateSessionFactoryUtil.getSessionFactory().inTransaction(session -> {
            session.merge(entity);
        });
    }
}
