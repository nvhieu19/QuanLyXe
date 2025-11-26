package com.fucar.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Car")
public class Car {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CarID")
    private Integer carId;
    
    @Column(name = "CarName", nullable = false, length = 100)
    private String carName;
    
    @Column(name = "CarModelYear", nullable = false)
    private Integer carModelYear;
    
    @Column(name = "Color", nullable = false, length = 50)
    private String color;
    
    @Column(name = "Capacity", nullable = false)
    private Integer capacity;
    
    @Column(name = "Description", nullable = false, columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "ImportDate", nullable = false)
    private LocalDate importDate;
    
    @ManyToOne
    @JoinColumn(name = "ProducerID", nullable = false)
    private CarProducer producer;
    
    @Column(name = "RentPrice", nullable = false)
    private Double rentPrice;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false)
    private Status status;
    
    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CarRental> carRentals;
    
    public enum Status {
        AVAILABLE, RENTED, MAINTENANCE
    }
    
    // Constructors
    public Car() {}
    
    public Car(String carName, Integer carModelYear, String color, Integer capacity, 
               String description, LocalDate importDate, CarProducer producer, 
               Double rentPrice, Status status) {
        this.carName = carName;
        this.carModelYear = carModelYear;
        this.color = color;
        this.capacity = capacity;
        this.description = description;
        this.importDate = importDate;
        this.producer = producer;
        this.rentPrice = rentPrice;
        this.status = status;
    }
    
    // Getters and Setters
    public Integer getCarId() {
        return carId;
    }
    
    public void setCarId(Integer carId) {
        this.carId = carId;
    }
    
    public String getCarName() {
        return carName;
    }
    
    public void setCarName(String carName) {
        this.carName = carName;
    }
    
    public Integer getCarModelYear() {
        return carModelYear;
    }
    
    public void setCarModelYear(Integer carModelYear) {
        this.carModelYear = carModelYear;
    }
    
    public String getColor() {
        return color;
    }
    
    public void setColor(String color) {
        this.color = color;
    }
    
    public Integer getCapacity() {
        return capacity;
    }
    
    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public LocalDate getImportDate() {
        return importDate;
    }
    
    public void setImportDate(LocalDate importDate) {
        this.importDate = importDate;
    }
    
    public CarProducer getProducer() {
        return producer;
    }
    
    public void setProducer(CarProducer producer) {
        this.producer = producer;
    }
    
    public Double getRentPrice() {
        return rentPrice;
    }
    
    public void setRentPrice(Double rentPrice) {
        this.rentPrice = rentPrice;
    }
    
    public Status getStatus() {
        return status;
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }
    
    public List<CarRental> getCarRentals() {
        return carRentals;
    }
    
    public void setCarRentals(List<CarRental> carRentals) {
        this.carRentals = carRentals;
    }
}