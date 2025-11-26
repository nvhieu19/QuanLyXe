package com.fucar.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "CarProducer")
public class CarProducer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProducerID")
    private Integer producerId;
    
    @Column(name = "ProducerName", nullable = false, length = 100)
    private String producerName;
    
    @Column(name = "Address", nullable = false, length = 200)
    private String address;
    
    @Column(name = "Country", nullable = false, length = 50)
    private String country;
    
    @OneToMany(mappedBy = "producer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Car> cars;
    
    // Constructors
    public CarProducer() {}
    
    public CarProducer(String producerName, String address, String country) {
        this.producerName = producerName;
        this.address = address;
        this.country = country;
    }
    
    // Getters and Setters
    public Integer getProducerId() {
        return producerId;
    }
    
    public void setProducerId(Integer producerId) {
        this.producerId = producerId;
    }
    
    public String getProducerName() {
        return producerName;
    }
    
    public void setProducerName(String producerName) {
        this.producerName = producerName;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getCountry() {
        return country;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }
    
    public List<Car> getCars() {
        return cars;
    }
    
    public void setCars(List<Car> cars) {
        this.cars = cars;
    }
}