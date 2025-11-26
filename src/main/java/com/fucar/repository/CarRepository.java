package com.fucar.repository;

import com.fucar.config.HibernateConfig;
import com.fucar.entity.Car;
import org.hibernate.Session;
import org.hibernate.Transaction;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class CarRepository {
    
    public Car findById(Integer id) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            return session.get(Car.class, id);
        }
    }
    
    public List<Car> findAll() {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Car> cq = cb.createQuery(Car.class);
            Root<Car> root = cq.from(Car.class);
            cq.select(root);
            return session.createQuery(cq).getResultList();
        }
    }
    
    public List<Car> findByStatus(Car.Status status) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Car> cq = cb.createQuery(Car.class);
            Root<Car> root = cq.from(Car.class);
            cq.select(root).where(cb.equal(root.get("status"), status));
            return session.createQuery(cq).getResultList();
        }
    }
    
    public void save(Car car) {
        Transaction transaction = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(car);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }
    
    public void update(Car car) {
        Transaction transaction = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(car);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }
    
    public void delete(Integer id) {
        Transaction transaction = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Car car = session.get(Car.class, id);
            if (car != null) {
                session.delete(car);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }
}