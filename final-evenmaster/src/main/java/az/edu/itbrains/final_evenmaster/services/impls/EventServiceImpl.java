package az.edu.itbrains.final_evenmaster.services.impls;

import az.edu.itbrains.final_evenmaster.enums.EventStatus;
import az.edu.itbrains.final_evenmaster.dtos.event.EventDto;
import az.edu.itbrains.final_evenmaster.models.Event;
import az.edu.itbrains.final_evenmaster.models.Review;
import az.edu.itbrains.final_evenmaster.models.User;
import az.edu.itbrains.final_evenmaster.repositories.EventRepository;
import az.edu.itbrains.final_evenmaster.repositories.UserRepository;
import az.edu.itbrains.final_evenmaster.services.EventService;
import az.edu.itbrains.final_evenmaster.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
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
        if (!event.getOrganizer().getId().equals(organizer.getId())) return;

        event.setTitle(dto.getTitle());
        event.setDescription(dto.getDescription());
        event.setLocation(dto.getLocation());
        event.setDateLine(dto.getDateLine());
        event.setPriceStandard(dto.getPriceStandard());
        event.setPriceVip(dto.getPriceVip());
        event.setImage(dto.getImage());
        event.setStatus(dto.getStatus());
        eventRepository.save(event);
    }

    @Override
    public void deleteEvent(Long id, Principal principal) {
        User organizer = userService.getCurrentUser(principal);
        Event event = eventRepository.findById(id).orElseThrow();
        if (event.getOrganizer().getId().equals(organizer.getId())) {
            eventRepository.deleteById(id);
        }
    }
    @Override
    public EventDto toDto(Event event) {
        EventDto dto = new EventDto();
        dto.setId(event.getId());
        dto.setTitle(event.getTitle());
        dto.setDescription(event.getDescription());
        dto.setLocation(event.getLocation());
        dto.setDateLine(event.getDateLine());
        dto.setPriceStandard(event.getPriceStandard());
        dto.setPriceVip(event.getPriceVip());
        dto.setImage(event.getImage());
        dto.setStatus(event.getStatus());

        List<Review> reviews = event.getReviews();
        double avg = reviews.stream().mapToInt(Review::getRating).average().orElse(0);
        dto.setAverageRating(avg);
        dto.setReviewCount(reviews.size());

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
    public EventDto getEventDtoById(Long id) {
        Event event = eventRepository.findById(id).orElseThrow();
        EventDto dto = new EventDto();

        dto.setId(event.getId());
        dto.setTitle(event.getTitle());
        dto.setLocation(event.getLocation());
        dto.setImage(event.getImage());
        dto.setDateLine(event.getDateLine());
        dto.setPriceStandard(event.getPriceStandard());
        dto.setStatus(event.getStatus());

        List<Review> reviews = event.getReviews();
        double avg = reviews.stream().mapToInt(Review::getRating).average().orElse(0);
        dto.setAverageRating(avg);
        dto.setReviewCount(reviews.size());

        return dto;
    }
    @Override
    public Page<EventDto> getApprovedEventDtos(Pageable pageable) {
        Page<Event> events = eventRepository.findByStatus(EventStatus.APPROVED, pageable);
        return events.map(this::toDto);
    }


}