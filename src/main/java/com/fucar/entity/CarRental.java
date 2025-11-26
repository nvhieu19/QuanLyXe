package com.fucar.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "CarRental")
public class CarRental {
    
    @EmbeddedId
    private CarRentalId id;
    
    @ManyToOne
    @MapsId("customerId")
    @JoinColumn(name = "CustomerID")
    private Customer customer;
    
    @ManyToOne
    @MapsId("carId")
    @JoinColumn(name = "CarID")
    private Car car;
    
    @Column(name = "PickupDate", nullable = false)
    private LocalDate pickupDate;
    
    @Column(name = "ReturnDate", nullable = false)
    private LocalDate returnDate;
    
    @Column(name = "RentPrice", nullable = false)
    private Double rentPrice;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false)
    private Status status;
    
    @OneToOne(mappedBy = "carRental", cascade = CascadeType.ALL, orphanRemoval = true)
    private Review review;
    
    public enum Status {
        PENDING, CONFIRMED, ACTIVE, RETURNED, CANCELLED
    }
    
    // Constructors
    public CarRental() {}
    
    public CarRental(Customer customer, Car car, LocalDate pickupDate, 
                    LocalDate returnDate, Double rentPrice, Status status) {
        this.id = new CarRentalId(customer.getCustomerId(), car.getCarId());
        this.customer = customer;
        this.car = car;
        this.pickupDate = pickupDate;
        this.returnDate = returnDate;
        this.rentPrice = rentPrice;
        this.status = status;
    }
    
    // Getters and Setters
    public CarRentalId getId() {
        return id;
    }
    
    public void setId(CarRentalId id) {
        this.id = id;
    }
    
    public Customer getCustomer() {
        return customer;
    }
    
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    public Car getCar() {
        return car;
    }
    
    public void setCar(Car car) {
        this.car = car;
    }
    
    public LocalDate getPickupDate() {
        return pickupDate;
    }
    
    public void setPickupDate(LocalDate pickupDate) {
        this.pickupDate = pickupDate;
    }
    
    public LocalDate getReturnDate() {
        return returnDate;
    }
    
    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
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
    
    public Review getReview() {
        return review;
    }
    
    public void setReview(Review review) {
        this.review = review;
    }
}