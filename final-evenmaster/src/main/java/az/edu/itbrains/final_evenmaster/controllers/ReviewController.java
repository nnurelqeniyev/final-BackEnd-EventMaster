package az.edu.itbrains.final_evenmaster.controllers;

import az.edu.itbrains.final_evenmaster.models.Review;
import az.edu.itbrains.final_evenmaster.repositories.ReviewRepository;
import az.edu.itbrains.final_evenmaster.services.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;
    private final ReviewRepository reviewRepository;

    @PostMapping("/add")
    @PreAuthorize("hasRole('USER')")
    public String addReview(@RequestParam Long eventId,
                            @RequestParam int rating,
                            @RequestParam String comment,
                            Principal principal) {
        reviewService.addReview(eventId, rating, comment, principal);
        return "redirect:/events/event?id=" + eventId;
    }
    @GetMapping("/edit")
    @PreAuthorize("@reviewSecurity.isOwner(#id, #principal)")
    public String editReview(@RequestParam Long id, Model model, Principal principal) {
        Review review = reviewRepository.findById(id).orElseThrow();
        model.addAttribute("review", review);
        return "edit-review";
    }

    @PostMapping("/update")
    @PreAuthorize("@reviewSecurity.isOwner(#id, #principal)")
    public String updateReview(@RequestParam Long id,
                               @RequestParam int rating,
                               @RequestParam String comment,
                               @RequestParam Long eventId,
                               Principal principal) {
        Review review = reviewRepository.findById(id).orElseThrow();
        review.setRating(rating);
        review.setComment(comment);
        reviewRepository.save(review);
        return "redirect:/events/event?id=" + eventId;
    }
    @PostMapping("/delete")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public String deleteReview(@RequestParam Long id, @RequestParam Long eventId) {
        reviewRepository.deleteById(id);
        return "redirect:/events/event?id=" + eventId;
    }
}
