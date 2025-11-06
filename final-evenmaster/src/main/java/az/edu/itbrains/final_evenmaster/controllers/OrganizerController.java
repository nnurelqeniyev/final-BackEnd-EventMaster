package az.edu.itbrains.final_evenmaster.controllers;

import az.edu.itbrains.final_evenmaster.dtos.event.EventDto;
import az.edu.itbrains.final_evenmaster.enums.EventStatus;
import az.edu.itbrains.final_evenmaster.models.Event;
import az.edu.itbrains.final_evenmaster.services.EventService;
import az.edu.itbrains.final_evenmaster.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/organizer")
@PreAuthorize("hasRole('ORGANIZER')")
@RequiredArgsConstructor
public class OrganizerController {

    private final EventService eventService;
    private final UserService userService;

    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {
        List<Event> events = eventService.getEventsByOrganizer(principal);
        model.addAttribute("events", events);
        model.addAttribute("organizerName", userService.getName(principal));
        return "organizer/organizer-dashboard";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("eventDto", new EventDto());
        return "create-event";
    }

    @PostMapping("/create")
    public String createEvent(@ModelAttribute EventDto eventDto, Principal principal) {
        eventService.createEvent(eventDto, principal);
        return "redirect:/organizer/dashboard";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model, Principal principal) {
        Event event = eventService.getEventById(id);
        if (!event.getOrganizer().getEmail().equals(principal.getName())) {
            return "redirect:/organizer/dashboard";
        }
        model.addAttribute("eventDto", eventService.toDto(event));
        model.addAttribute("eventId", id);
        return "organizer/edit-event";
    }

    @PostMapping("/edit/{id}")
    public String updateEvent(@PathVariable Long id, @ModelAttribute EventDto eventDto, Principal principal) {
        eventDto.setStatus(EventStatus.PENDING);
        eventService.updateEvent(id, eventDto, principal);
        return "redirect:/organizer/dashboard";
    }

    @PostMapping("/delete/{id}")
    public String deleteEvent(@PathVariable Long id, Principal principal) {
        eventService.deleteEvent(id, principal);
        return "redirect:/organizer/dashboard";
    }
}