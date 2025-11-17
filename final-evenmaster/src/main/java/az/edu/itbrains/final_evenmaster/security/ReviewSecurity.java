package az.edu.itbrains.final_evenmaster.security;

import az.edu.itbrains.final_evenmaster.models.Review;
import az.edu.itbrains.final_evenmaster.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.Principal;

@Component("reviewSecurity")
public class ReviewSecurity {

    @Autowired
    private ReviewRepository reviewRepository;

    public boolean isOwner(Long reviewId, Principal principal) {
        Review review = reviewRepository.findById(reviewId).orElse(null);
        if (review == null) return false;
        return review.getUser().getEmail().equals(principal.getName());
    }
}