package az.edu.itbrains.final_evenmaster.controllers;

import az.edu.itbrains.final_evenmaster.models.Event;
import az.edu.itbrains.final_evenmaster.models.User;
import az.edu.itbrains.final_evenmaster.repositories.EventRepository;
import az.edu.itbrains.final_evenmaster.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/organizer")
@RequiredArgsConstructor
public class OrganizerController {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('ORGANIZER')")
    public String dashboard(Model model, Principal principal) {
        Optional<User> optionalUser = userRepository.findByEmail(principal.getName());
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("İstifadəçi tapılmadı: " + principal.getName());
        }
        User organizer = optionalUser.get();
        List<Event> events = eventRepository.findByOrganizer(organizer);

        model.addAttribute("events", events);
        model.addAttribute("organizerName", organizer.getFullName());
        return "organizer-dashboard";
    }
}