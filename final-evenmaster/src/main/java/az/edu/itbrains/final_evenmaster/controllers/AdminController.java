package az.edu.itbrains.final_evenmaster.controllers;

import az.edu.itbrains.final_evenmaster.enums.EventStatus;
import az.edu.itbrains.final_evenmaster.models.Event;
import az.edu.itbrains.final_evenmaster.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private EventRepository eventRepository;

    @GetMapping
    public String adminPanel(Model model) {
        List<Event> pendingEvents = eventRepository.findByStatus(EventStatus.PENDING);   // ✅ Enum göndər
        List<Event> approvedEvents = eventRepository.findByStatus(EventStatus.APPROVED); // ✅ Enum göndər
        model.addAttribute("pendingEvents", pendingEvents);
        model.addAttribute("approvedEvents", approvedEvents);
        return "admin"; // admin.html
    }
    @PostMapping("/approve")
    public String approveEvent(@RequestParam Long id) {
        Event event = eventRepository.findById(id).orElseThrow();
        event.setStatus(EventStatus.APPROVED);
        eventRepository.save(event);
        return "redirect:/admin";
    }
    @PostMapping("/delete")
    public String deleteEvent(@RequestParam Long id) {
        eventRepository.deleteById(id);
        return "redirect:/admin";
    }
}