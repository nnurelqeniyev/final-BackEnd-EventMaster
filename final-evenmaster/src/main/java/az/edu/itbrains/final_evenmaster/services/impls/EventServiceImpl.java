package az.edu.itbrains.final_evenmaster.services.impls;

import az.edu.itbrains.final_evenmaster.dtos.event.EventDto;
import az.edu.itbrains.final_evenmaster.models.Event;
import az.edu.itbrains.final_evenmaster.models.User;
import az.edu.itbrains.final_evenmaster.repositories.EventRepository;
import az.edu.itbrains.final_evenmaster.repositories.UserRepository;
import az.edu.itbrains.final_evenmaster.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final UserRepository userRepository;

    private final EventRepository eventRepository;

    @Override
    public String createEvent(EventDto eventDto, User organizer) {
        Event event = new Event();
        event.setTitle(eventDto.getTitle());
        event.setDescription(eventDto.getDescription());
        event.setLocation(eventDto.getLocation());
        event.setDateLine(eventDto.getDateLine());
        event.setCompany(organizer.getCompany());
        event.setImage(eventDto.getImage());
        event.setPriceStandard(eventDto.getPriceStandard());
        event.setPriceVip(eventDto.getPriceVip());

        event.setOrganizer(organizer);
        event.setStatus("pending");

        eventRepository.save(event);
        return "redirect:/organizer/dashboard";
    }

    @Override
    public void updateEvent(EventDto dto, Long id, Principal principal) {
        User organizer = userRepository.findByEmail(principal.getName()).orElseThrow();
        Event event = eventRepository.findById(id).orElseThrow();

        if (!event.getOrganizer().equals(organizer)) {
            throw new RuntimeException("Bu tədbiri redaktə etmək icazəniz yoxdur.");
        }

        event.setTitle(dto.getTitle());
        event.setDescription(dto.getDescription());
        event.setLocation(dto.getLocation());
        event.setDateLine(dto.getDateLine());
        event.setImage(dto.getImage());
        event.setPriceStandard(dto.getPriceStandard());
        event.setPriceVip(dto.getPriceVip());

        eventRepository.save(event);
    }

    @Override
    public void deleteEvent(Long eventId, Principal principal) {
        User organizer = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("İstifadəçi tapılmadı: " + principal.getName()));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Tədbir tapılmadı: ID=" + eventId));
        if (!event.getOrganizer().equals(organizer)) {
            throw new RuntimeException("Bu tədbiri silmək icazəniz yoxdur.");
        }
        eventRepository.delete(event);
    }

}