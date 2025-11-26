package com.fucar.repository;

import com.fucar.config.HibernateConfig;
import com.fucar.entity.CarProducer;
import org.hibernate.Session;
import org.hibernate.Transaction;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class CarProducerRepository {
    
    public CarProducer findById(Integer id) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            return session.get(CarProducer.class, id);
        }
    }
    
    public List<CarProducer> findAll() {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<CarProducer> cq = cb.createQuery(CarProducer.class);
            Root<CarProducer> root = cq.from(CarProducer.class);
            cq.select(root);
            return session.createQuery(cq).getResultList();
        }
    }
    
    public void save(CarProducer producer) {
        Transaction transaction = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(producer);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }
    
    public void update(CarProducer producer) {
        Transaction transaction = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(producer);
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
            CarProducer producer = session.get(CarProducer.class, id);
            if (producer != null) {
                session.delete(producer);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }
}