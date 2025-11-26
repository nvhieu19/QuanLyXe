package com.fucar.repository;

import com.fucar.config.HibernateConfig;
import com.fucar.entity.Customer;
import org.hibernate.Session;
import org.hibernate.Transaction;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class CustomerRepository {
    
    public Customer findById(Integer id) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            return session.get(Customer.class, id);
        }
    }
    
    public List<Customer> findAll() {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);
            Root<Customer> root = cq.from(Customer.class);
            cq.select(root);
            return session.createQuery(cq).getResultList();
        }
    }
    
    public Customer findByAccountId(Integer accountId) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);
            Root<Customer> root = cq.from(Customer.class);
            cq.select(root).where(cb.equal(root.get("account").get("accountId"), accountId));
            List<Customer> results = session.createQuery(cq).getResultList();
            return results.isEmpty() ? null : results.get(0);
        }
    }
    
    public void save(Customer customer) {
        Transaction transaction = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(customer);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }
    
    public void update(Customer customer) {
        Transaction transaction = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(customer);
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
            Customer customer = session.get(Customer.class, id);
            if (customer != null) {
                session.delete(customer);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }
}