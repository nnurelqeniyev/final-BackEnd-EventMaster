package az.edu.itbrains.final_evenmaster.repositories;

import az.edu.itbrains.final_evenmaster.models.Event;
import az.edu.itbrains.final_evenmaster.models.Review;
import az.edu.itbrains.final_evenmaster.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    //1 rey en cox
    boolean existsByUserAndEvent(User user, Event event);
    List<Review> findByEventId(Long eventId);
}