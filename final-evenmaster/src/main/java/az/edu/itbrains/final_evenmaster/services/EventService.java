package az.edu.itbrains.final_evenmaster.services;

import az.edu.itbrains.final_evenmaster.dtos.event.EventDto;
import az.edu.itbrains.final_evenmaster.models.Event;
import org.springframework.data.domain.Sort;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

public interface EventService {
    void createEvent(EventDto dto, Principal principal);
    void updateEvent(Long id, EventDto dto, Principal principal);
    void deleteEvent(Long id, Principal principal);
    EventDto toDto(Event event);
    List<Event> getEventsByOrganizer(Principal principal);
    Event getEventById(Long id);}