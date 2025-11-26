package com.fucar.service;

import com.fucar.entity.Car;
import com.fucar.repository.CarRepository;
import com.fucar.repository.CarRentalRepository;
import java.util.List;

public class CarService {
    
    private final CarRepository carRepository;
    private final CarRentalRepository rentalRepository;
    
    public CarService() {
        this.carRepository = new CarRepository();
        this.rentalRepository = new CarRentalRepository();
    }
    
    public Car findById(Integer id) {
        return carRepository.findById(id);
    }
    
    public List<Car> findAll() {
        return carRepository.findAll();
    }
    
    public List<Car> findByStatus(Car.Status status) {
        return carRepository.findByStatus(status);
    }
    
    public void save(Car car) {
        carRepository.save(car);
    }
    
    public void update(Car car) {
        carRepository.update(car);
    }
    
    public void delete(Integer id) throws Exception {
        // Check if car has rentals
        if (rentalRepository.hasRentals(id)) {
            throw new Exception("Cannot delete car with existing rentals. Please change status instead.");
        }
        carRepository.delete(id);
    }
    
    public void updateStatus(Integer carId, Car.Status newStatus) {
        Car car = carRepository.findById(carId);
        if (car != null) {
            car.setStatus(newStatus);
            carRepository.update(car);
        }
    }
}