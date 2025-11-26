package com.fucar.repository;

import com.fucar.config.HibernateConfig;
import com.fucar.entity.Account;
import org.hibernate.Session;
import org.hibernate.Transaction;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class AccountRepository {
    
    public Account findById(Integer id) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            return session.get(Account.class, id);
        }
    }
    
    public List<Account> findAll() {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Account> cq = cb.createQuery(Account.class);
            Root<Account> root = cq.from(Account.class);
            cq.select(root);
            return session.createQuery(cq).getResultList();
        }
    }
    
    public Account findByAccountName(String accountName) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Account> cq = cb.createQuery(Account.class);
            Root<Account> root = cq.from(Account.class);
            cq.select(root).where(cb.equal(root.get("accountName"), accountName));
            List<Account> results = session.createQuery(cq).getResultList();
            return results.isEmpty() ? null : results.get(0);
        }
    }
    
    public void save(Account account) {
        Transaction transaction = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(account);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }
    
    public void update(Account account) {
        Transaction transaction = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(account);
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
            Account account = session.get(Account.class, id);
            if (account != null) {
                session.delete(account);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }
}