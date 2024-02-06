package com.pet.project.weathertracker.dao;

import com.pet.project.weathertracker.exceptions.EntityExistException;
import com.pet.project.weathertracker.models.Session;
import com.pet.project.weathertracker.models.User;
import com.pet.project.weathertracker.utils.HibernateSessionFactoryUtil;
import jakarta.persistence.PersistenceException;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Optional;
import java.util.UUID;

public class SessionDao extends BaseDao<Session, UUID> {
    public SessionDao() {
        super(Session.class);
    }

    public Optional<Session> findByUser(User user) {
        org.hibernate.Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Query<Session> query = session.createQuery("from Session where user = :paramUser", Session.class);
        query.setParameter("paramUser", user);
        Optional<Session> sessionOptional = Optional.ofNullable(query.uniqueResult());
        session.close();
        return sessionOptional;
    }
    public void saveOrUpdate(Session entity) {
        Optional<Session> sessionOptional = findByUser(entity.getUser());
        org.hibernate.Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            if(sessionOptional.isPresent()) {
                session.merge(entity);
            } else {
                session.persist(entity);
            }
            tx.commit();
        } catch (PersistenceException e) {
            tx.rollback();
        } finally {
            session.close();
        }
    }
}
