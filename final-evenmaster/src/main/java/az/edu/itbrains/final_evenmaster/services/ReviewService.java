package az.edu.itbrains.final_evenmaster.services;

import az.edu.itbrains.final_evenmaster.dtos.ReviewDto;

import java.security.Principal;
import java.util.List;

public interface ReviewService {
    void addReview(Long eventId, int rating, String comment, Principal principal);
    List<ReviewDto> getReviewDTOs(Long eventId, Principal principal);
    //1 rey en cox
    boolean hasUserReviewedEvent(Long eventId, Principal principal);
}