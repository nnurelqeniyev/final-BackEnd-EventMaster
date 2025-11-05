package az.edu.itbrains.final_evenmaster.repositories;

import az.edu.itbrains.final_evenmaster.enums.EventStatus;
import az.edu.itbrains.final_evenmaster.models.Company;
import az.edu.itbrains.final_evenmaster.models.Event;
import az.edu.itbrains.final_evenmaster.models.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByStatus(EventStatus status);
    List<Event> findByOrganizer(User organizer);
    List<Event> findByOrganizerIdAndStatus(Long organizerId, String status);
}
