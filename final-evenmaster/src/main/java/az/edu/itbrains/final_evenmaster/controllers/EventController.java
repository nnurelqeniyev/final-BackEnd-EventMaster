package az.edu.itbrains.final_evenmaster.controllers;

import az.edu.itbrains.final_evenmaster.dtos.ReviewDto;
import az.edu.itbrains.final_evenmaster.dtos.event.EventDto;
import az.edu.itbrains.final_evenmaster.enums.EventStatus;
import az.edu.itbrains.final_evenmaster.models.Event;
import az.edu.itbrains.final_evenmaster.models.User;
import az.edu.itbrains.final_evenmaster.repositories.CompanyRepository;
import az.edu.itbrains.final_evenmaster.repositories.EventRepository;
import az.edu.itbrains.final_evenmaster.repositories.UserRepository;
import az.edu.itbrains.final_evenmaster.services.EventService;
import az.edu.itbrains.final_evenmaster.services.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private final ReviewService reviewService;
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
        eventService.createEvent(eventDto, principal);
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
        dto.setStatus(EventStatus.PENDING);
        eventService.updateEvent(id, dto, principal);
        return "redirect:/organizer/dashboard";
    }
    @GetMapping("/admin/pending")
    @PreAuthorize("hasRole('ADMIN')")
    public String getPendingEvents(Model model) {
        List<Event> pendingEvents = eventRepository.findByStatus(EventStatus.PENDING);
        model.addAttribute("events", pendingEvents);
        return "admin/admin-events";
    }
    @PostMapping("/admin/{id}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public String approveEvent(@PathVariable Long id) {
        Event event = eventRepository.findById(id).orElseThrow();
        event.setStatus(EventStatus.APPROVED);
        eventRepository.save(event);
        return "redirect:/events/admin/pending";
    }
    @GetMapping
    public String showEvents(@RequestParam(defaultValue = "0") int page, Model model) {
        Pageable pageable = PageRequest.of(page, 30, Sort.by("dateLine").descending());
        Page<EventDto> eventPage = eventService.getApprovedEventDtos(pageable);

        model.addAttribute("eventPage", eventPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", eventPage.getTotalPages());

        return "events";
    }
    @GetMapping("/event")
    public String eventPage(@RequestParam Long id, Model model, Principal principal) {
        Event event = eventRepository.findById(id).orElseThrow();
        List<ReviewDto> reviewDTOs = reviewService.getReviewDTOs(id, principal);
        //1 rey en cox
        boolean hasReviewed = reviewService.hasUserReviewedEvent(id, principal);

        model.addAttribute("event", event);
        model.addAttribute("reviewDTOs", reviewDTOs);
        //1 rey en cox
        model.addAttribute("hasReviewed", hasReviewed);
        return "event";
    }
}