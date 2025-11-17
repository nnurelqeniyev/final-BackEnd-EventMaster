package az.edu.itbrains.final_evenmaster.services.impls;

import az.edu.itbrains.final_evenmaster.dtos.ReviewDto;
import az.edu.itbrains.final_evenmaster.mapper.ReviewMapper;
import az.edu.itbrains.final_evenmaster.models.Event;
import az.edu.itbrains.final_evenmaster.models.Review;
import az.edu.itbrains.final_evenmaster.models.User;
import az.edu.itbrains.final_evenmaster.repositories.EventRepository;
import az.edu.itbrains.final_evenmaster.repositories.ReviewRepository;
import az.edu.itbrains.final_evenmaster.repositories.UserRepository;
import az.edu.itbrains.final_evenmaster.services.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Override
    public void addReview(Long eventId, int rating, String comment, Principal principal) {
        if (rating < 1 || rating > 5 || comment == null || comment.trim().isEmpty()) return;

        User user = userRepository.findByEmail(principal.getName()).orElseThrow();
        Event event = eventRepository.findById(eventId).orElseThrow();

        Review review = new Review();
        review.setEvent(event);
        review.setUser(user);
        review.setRating(rating);
        review.setComment(comment);
        review.setCreatedAt(LocalDateTime.now());

        reviewRepository.save(review);
        reviewRepository.flush();
        System.out.println("Review saved!");
    }
    @Override
    public List<ReviewDto> getReviewDTOs(Long eventId, Principal principal) {
        return reviewRepository.findByEventId(eventId)
                .stream()
                .map(r -> ReviewMapper.toDTO(r, principal))
                .toList();
    }
    //1 rey en cox
    @Override
    public boolean hasUserReviewedEvent(Long eventId, Principal principal) {
        if (principal == null) return false;

        User user = userRepository.findByEmail(principal.getName()).orElse(null);
        Event event = eventRepository.findById(eventId).orElse(null);

        if (user == null || event == null) return false;

        return reviewRepository.existsByUserAndEvent(user, event);
    }
}