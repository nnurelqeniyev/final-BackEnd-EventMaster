package az.edu.itbrains.final_evenmaster.controllers;

import az.edu.itbrains.final_evenmaster.dtos.event.EventDto;
import az.edu.itbrains.final_evenmaster.models.Event;
import az.edu.itbrains.final_evenmaster.models.User;
import az.edu.itbrains.final_evenmaster.repositories.CompanyRepository;
import az.edu.itbrains.final_evenmaster.repositories.EventRepository;
import az.edu.itbrains.final_evenmaster.repositories.UserRepository;
import az.edu.itbrains.final_evenmaster.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

    @GetMapping("/create")
    @PreAuthorize("hasRole('ORGANIZER')")
    public String showCreateForm(Model model, Principal principal) {
        User organizer = userRepository.findByEmail(principal.getName()).orElseThrow();
        model.addAttribute("eventDto", new EventDto());
        model.addAttribute("organizer", organizer);
        return "organizer/create-event";
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ORGANIZER')")
    public String createEvent(@ModelAttribute EventDto eventDto, Principal principal) {
        User organizer = userRepository.findByEmail(principal.getName()).orElseThrow();
        eventService.createEvent(eventDto, organizer);
        return "redirect:/organizer/dashboard";
    }
    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ORGANIZER')")
    public String deleteEvent(@PathVariable Long id, Principal principal) {
        eventService.deleteEvent(id, principal);
        return "redirect:/organizer/dashboard";
    }
    @PostMapping("/edit/{id}")
    public String updateEvent(@PathVariable Long id, @ModelAttribute EventDto dto, Principal principal) {
        eventService.updateEvent(dto, id, principal);
        return "redirect:/organizer/dashboard";
    }
    @GetMapping("/admin/pending")
    @PreAuthorize("hasRole('ADMIN')")
    public String getPendingEvents(Model model) {
        List<Event> pendingEvents = eventRepository.findByStatus("pending");
        model.addAttribute("events", pendingEvents);
        return "admin/admin-events";
    }

    @PostMapping("/admin/{id}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public String approveEvent(@PathVariable Long id) {
        Event event = eventRepository.findById(id).orElseThrow();
        event.setStatus("approved");
        eventRepository.save(event);
        return "redirect:/events/admin/pending";
    }
}