package az.edu.itbrains.final_evenmaster.controllers;

import az.edu.itbrains.final_evenmaster.dtos.ticket.TicketStatsDto;
import az.edu.itbrains.final_evenmaster.models.Event;
import az.edu.itbrains.final_evenmaster.models.User;
import az.edu.itbrains.final_evenmaster.services.EventService;
import az.edu.itbrains.final_evenmaster.services.TicketService;
import az.edu.itbrains.final_evenmaster.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class OrqanizerStatsController {

    private final EventService eventService;
    private final TicketService ticketService;
    private final UserService userService;

    @PreAuthorize("hasRole('ORGANIZER')")
    @GetMapping("/organizer/stats")
    public String showStats(Principal principal, Model model) {
        User organizer = userService.findByEmail(principal.getName());
        List<Event> events = eventService.getEventsByOrganizer(principal);

        Map<Long, TicketStatsDto> statsMap = new HashMap<>();
        for (Event event : events) {
            TicketStatsDto stats = ticketService.getStatsForEvent(event.getId());
            statsMap.put(event.getId(), stats);
        }

        TicketStatsDto summary = ticketService.getSummaryForOrganizer(organizer);

        model.addAttribute("statsMap", statsMap);
        model.addAttribute("summary", summary);

        return "organizer/stats";
    }
}