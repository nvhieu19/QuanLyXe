package com.fucar.entity;

import javax.persistence.*;

@Entity
@Table(name = "Review")
public class Review {
    
    @EmbeddedId
    private CarRentalId id;
    
    @OneToOne
    @MapsId
    @JoinColumns({
        @JoinColumn(name = "CustomerID", referencedColumnName = "CustomerID"),
        @JoinColumn(name = "CarID", referencedColumnName = "CarID")
    })
    private CarRental carRental;
    
    @Column(name = "ReviewStar", nullable = false)
    private Integer reviewStar;
    
    @Column(name = "Comment", nullable = false, columnDefinition = "TEXT")
    private String comment;
    
    // Constructors
    public Review() {}
    
    public Review(CarRental carRental, Integer reviewStar, String comment) {
        this.id = carRental.getId();
        this.carRental = carRental;
        this.reviewStar = reviewStar;
        this.comment = comment;
    }
    
    // Getters and Setters
    public CarRentalId getId() {
        return id;
    }
    
    public void setId(CarRentalId id) {
        this.id = id;
    }
    
    public CarRental getCarRental() {
        return carRental;
    }
    
    public void setCarRental(CarRental carRental) {
        this.carRental = carRental;
    }
    
    public Integer getReviewStar() {
        return reviewStar;
    }
    
    public void setReviewStar(Integer reviewStar) {
        this.reviewStar = reviewStar;
    }
    
    public String getComment() {
        return comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }
}