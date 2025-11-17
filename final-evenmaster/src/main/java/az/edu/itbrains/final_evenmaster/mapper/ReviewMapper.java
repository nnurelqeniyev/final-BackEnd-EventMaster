package az.edu.itbrains.final_evenmaster.mapper;

import az.edu.itbrains.final_evenmaster.dtos.ReviewDto;
import az.edu.itbrains.final_evenmaster.models.Review;

import java.security.Principal;

public class ReviewMapper {
    public static ReviewDto toDTO(Review review, Principal principal) {
        ReviewDto dto = new ReviewDto();
        dto.setId(review.getId());
        dto.setUserName(review.getUser().getFullName());
        dto.setRating(review.getRating());
        dto.setComment(review.getComment());
        dto.setEditable(principal != null && review.getUser().getEmail().equals(principal.getName()));
        return dto;
    }
}