package com.fucar.service;

import com.fucar.entity.Review;
import com.fucar.entity.CarRentalId;
import com.fucar.repository.ReviewRepository;
import java.util.List;

public class ReviewService {
    
    private final ReviewRepository reviewRepository;
    
    public ReviewService() {
        this.reviewRepository = new ReviewRepository();
    }
    
    public Review findById(CarRentalId id) {
        return reviewRepository.findById(id);
    }
    
    public List<Review> findAll() {
        return reviewRepository.findAll();
    }
    
    public void save(Review review) throws Exception {
        // Validate star rating
        if (review.getReviewStar() < 1 || review.getReviewStar() > 5) {
            throw new Exception("Review star must be between 1 and 5");
        }
        reviewRepository.save(review);
    }
    
    public void update(Review review) throws Exception {
        // Validate star rating
        if (review.getReviewStar() < 1 || review.getReviewStar() > 5) {
            throw new Exception("Review star must be between 1 and 5");
        }
        reviewRepository.update(review);
    }
    
    public void delete(CarRentalId id) {
        reviewRepository.delete(id);
    }
}