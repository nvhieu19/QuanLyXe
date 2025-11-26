package com.fucar.service;

import com.fucar.entity.Car;
import com.fucar.entity.CarRental;
import com.fucar.entity.CarRentalId;
import com.fucar.repository.CarRentalRepository;
import com.fucar.repository.CarRepository;
import java.time.LocalDate;
import java.util.List;

public class CarRentalService {
    
    private final CarRentalRepository rentalRepository;
    private final CarRepository carRepository;
    
    public CarRentalService() {
        this.rentalRepository = new CarRentalRepository();
        this.carRepository = new CarRepository();
    }
    
    public CarRental findById(CarRentalId id) {
        return rentalRepository.findById(id);
    }
    
    public List<CarRental> findAll() {
        return rentalRepository.findAll();
    }
    
    public List<CarRental> findByCustomerId(Integer customerId) {
        return rentalRepository.findByCustomerId(customerId);
    }
    
    public List<CarRental> findByDateRange(LocalDate startDate, LocalDate endDate) {
        return rentalRepository.findByDateRange(startDate, endDate);
    }
    
    public void save(CarRental rental) throws Exception {
        // Validate dates
        if (rental.getPickupDate().isAfter(rental.getReturnDate()) || 
            rental.getPickupDate().isEqual(rental.getReturnDate())) {
            throw new Exception("Pickup date must be before return date");
        }
        
        // Update car status to RENTED
        Car car = rental.getCar();
        car.setStatus(Car.Status.RENTED);
        carRepository.update(car);
        
        rentalRepository.save(rental);
    }
    
    public void update(CarRental rental) throws Exception {
        // Validate dates
        if (rental.getPickupDate().isAfter(rental.getReturnDate()) || 
            rental.getPickupDate().isEqual(rental.getReturnDate())) {
            throw new Exception("Pickup date must be before return date");
        }
        rentalRepository.update(rental);
    }
    
    public void returnCar(CarRentalId id) {
        CarRental rental = rentalRepository.findById(id);
        if (rental != null) {
            rental.setStatus(CarRental.Status.RETURNED);
            rentalRepository.update(rental);
            
            // Update car status to AVAILABLE
            Car car = rental.getCar();
            car.setStatus(Car.Status.AVAILABLE);
            carRepository.update(car);
        }
    }
    
    public void delete(CarRentalId id) {
        rentalRepository.delete(id);
    }
}