package com.fucar.repository;

import com.fucar.config.HibernateConfig;
import com.fucar.entity.CarRental;
import com.fucar.entity.CarRentalId;
import org.hibernate.Session;
import org.hibernate.Transaction;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.List;

public class CarRentalRepository {
    
    public CarRental findById(CarRentalId id) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            return session.get(CarRental.class, id);
        }
    }
    
    public List<CarRental> findAll() {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<CarRental> cq = cb.createQuery(CarRental.class);
            Root<CarRental> root = cq.from(CarRental.class);
            cq.select(root);
            return session.createQuery(cq).getResultList();
        }
    }
    
    public List<CarRental> findByCustomerId(Integer customerId) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<CarRental> cq = cb.createQuery(CarRental.class);
            Root<CarRental> root = cq.from(CarRental.class);
            cq.select(root).where(cb.equal(root.get("customer").get("customerId"), customerId));
            return session.createQuery(cq).getResultList();
        }
    }
    
    public List<CarRental> findByDateRange(LocalDate startDate, LocalDate endDate) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<CarRental> cq = cb.createQuery(CarRental.class);
            Root<CarRental> root = cq.from(CarRental.class);
            cq.select(root).where(
                cb.and(
                    cb.greaterThanOrEqualTo(root.get("pickupDate"), startDate),
                    cb.lessThanOrEqualTo(root.get("returnDate"), endDate)
                )
            );
            return session.createQuery(cq).getResultList();
        }
    }
    
    public boolean hasRentals(Integer carId) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            Root<CarRental> root = cq.from(CarRental.class);
            cq.select(cb.count(root)).where(cb.equal(root.get("car").get("carId"), carId));
            Long count = session.createQuery(cq).getSingleResult();
            return count > 0;
        }
    }
    
    public void save(CarRental rental) {
        Transaction transaction = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(rental);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }
    
    public void update(CarRental rental) {
        Transaction transaction = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(rental);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }
    
    public void delete(CarRentalId id) {
        Transaction transaction = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            CarRental rental = session.get(CarRental.class, id);
            if (rental != null) {
                session.delete(rental);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }
}