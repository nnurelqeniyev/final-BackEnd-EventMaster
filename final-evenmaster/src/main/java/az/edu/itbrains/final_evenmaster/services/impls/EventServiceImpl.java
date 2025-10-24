package az.edu.itbrains.final_evenmaster.services.impls;

import az.edu.itbrains.final_evenmaster.enums.EventStatus;
import az.edu.itbrains.final_evenmaster.dtos.event.EventDto;
import az.edu.itbrains.final_evenmaster.models.Event;
import az.edu.itbrains.final_evenmaster.models.User;
import az.edu.itbrains.final_evenmaster.repositories.EventRepository;
import az.edu.itbrains.final_evenmaster.services.EventService;
import az.edu.itbrains.final_evenmaster.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final UserService userService;

    @Override
    public void createEvent(EventDto dto, Principal principal) {
        User organizer = userService.getCurrentUser(principal);
        Event event = new Event();
        event.setTitle(dto.getTitle());
        event.setDescription(dto.getDescription());
        event.setLocation(dto.getLocation());
        event.setDateLine(dto.getDateLine());
        event.setPriceStandard(dto.getPriceStandard());
        event.setPriceVip(dto.getPriceVip());
        event.setImage(dto.getImage());
        event.setStatus(EventStatus.PENDING);
        event.setOrganizer(organizer);
        event.setCompany(organizer.getCompany());
        eventRepository.save(event);
    }

    @Override
    public void updateEvent(Long id, EventDto dto, Principal principal) {
        User organizer = userService.getCurrentUser(principal);
        Event event = eventRepository.findById(id).orElseThrow();
        if (!event.getOrganizer().getId().equals(organizer.getId())) return; // ✅ düzəliş

        event.setTitle(dto.getTitle());
        event.setDescription(dto.getDescription());
        event.setLocation(dto.getLocation());
        event.setDateLine(dto.getDateLine());
        event.setPriceStandard(dto.getPriceStandard());
        event.setPriceVip(dto.getPriceVip());
        event.setImage(dto.getImage());
        eventRepository.save(event);
    }

    @Override
    public void deleteEvent(Long id, Principal principal) {
        User organizer = userService.getCurrentUser(principal);
        Event event = eventRepository.findById(id).orElseThrow();
        if (event.getOrganizer().getId().equals(organizer.getId())) { // ✅ düzəliş
            eventRepository.deleteById(id);
        }
    }
    @Override
    public EventDto toDto(Event event) {
        EventDto dto = new EventDto();
        dto.setTitle(event.getTitle());
        dto.setDescription(event.getDescription());
        dto.setLocation(event.getLocation());
        dto.setDateLine(event.getDateLine());
        dto.setPriceStandard(event.getPriceStandard());
        dto.setPriceVip(event.getPriceVip());
        dto.setImage(event.getImage());
        return dto;
    }

    @Override
    public List<Event> getEventsByOrganizer(Principal principal) {
        User organizer = userService.getCurrentUser(principal);
        return eventRepository.findByOrganizer(organizer);
    }


    @Override
    public Event getEventById(Long id) {
        return eventRepository.findById(id).orElseThrow();
    }
}