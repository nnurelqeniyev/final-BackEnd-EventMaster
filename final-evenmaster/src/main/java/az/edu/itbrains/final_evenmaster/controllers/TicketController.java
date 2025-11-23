package az.edu.itbrains.final_evenmaster.controllers;

import az.edu.itbrains.final_evenmaster.models.User;
import az.edu.itbrains.final_evenmaster.services.TicketService;
import az.edu.itbrains.final_evenmaster.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;
    private final UserService userService;

    @PostMapping("/buy")
    @PreAuthorize("hasRole('USER')")
    public String buyTicket(@RequestParam Long eventId,
                            @RequestParam String type,
                            @RequestParam int quantity,
                            Principal principal) {
        User user = userService.getCurrentUser(principal);
        ticketService.buyTicket(user, eventId, type, quantity);
        return "redirect:/events/event?id=" + eventId;
    }
}