package com.pet.project.weathertracker.dao;

import com.pet.project.weathertracker.exceptions.EntityExistException;
import com.pet.project.weathertracker.models.Location;
import com.pet.project.weathertracker.models.User;
import com.pet.project.weathertracker.utils.HibernateSessionFactoryUtil;
import jakarta.persistence.PersistenceException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Optional;

public class UserDao extends BaseDao<User, Long>{
    public UserDao() {
        super(User.class);
    }

    public Optional<User> findByLogin(String login) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Query<User> query = session.createQuery("from User where login = :paramLogin", User.class);
        query.setParameter("paramLogin", login);
        Optional<User> user = Optional.ofNullable(query.uniqueResult());
        session.close();
        return user;
    }

    public void addLocationToUser(User user, Location location) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            session.persist(location);
            user.getLocations().add(location);
            session.merge(user);
            tx.commit();
        } catch (PersistenceException e) {
            tx.rollback();
            throw new EntityExistException("Entity " + location.toString() + " already exists");
        } finally {
            session.close();
        }
    }

    public void delLocationToUser(User user, Location location) {
        user.getLocations().removeIf(element -> element.getLat() == location.getLat() && element.getLon() == location.getLon());
        update(user);
    }
}
