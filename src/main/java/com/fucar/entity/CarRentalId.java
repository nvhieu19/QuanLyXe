package com.fucar.entity;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CarRentalId implements Serializable {
    
    private Integer customerId;
    private Integer carId;
    
    public CarRentalId() {}
    
    public CarRentalId(Integer customerId, Integer carId) {
        this.customerId = customerId;
        this.carId = carId;
    }
    
    public Integer getCustomerId() {
        return customerId;
    }
    
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
    
    public Integer getCarId() {
        return carId;
    }
    
    public void setCarId(Integer carId) {
        this.carId = carId;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarRentalId that = (CarRentalId) o;
        return Objects.equals(customerId, that.customerId) && 
               Objects.equals(carId, that.carId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(customerId, carId);
    }
}