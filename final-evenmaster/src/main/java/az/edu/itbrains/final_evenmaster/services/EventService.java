package az.edu.itbrains.final_evenmaster.services;

import az.edu.itbrains.final_evenmaster.dtos.event.EventDto;
import az.edu.itbrains.final_evenmaster.models.Event;
import az.edu.itbrains.final_evenmaster.models.User;import org.springframework.ui.Model;

import java.security.Principal;

public interface EventService {
    public String createEvent(EventDto eventDto, User organizer);

    void updateEvent(EventDto dto, Long id, Principal principal);

    void deleteEvent(Long id, Principal principal);
}
