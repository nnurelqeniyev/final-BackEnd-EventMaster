package az.edu.itbrains.final_evenmaster.services;

import az.edu.itbrains.final_evenmaster.dtos.ticket.TicketStatsDto;
import az.edu.itbrains.final_evenmaster.models.User;

public interface TicketService {
    TicketStatsDto getStatsForEvent(Long eventId);
    TicketStatsDto getSummaryForOrganizer(User organizer);

    void buyTicket(User user, Long eventId, String type, int quantity);
}