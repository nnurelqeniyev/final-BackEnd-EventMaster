package az.edu.itbrains.final_evenmaster.services.impls;

import az.edu.itbrains.final_evenmaster.dtos.ticket.TicketStatsDto;
import az.edu.itbrains.final_evenmaster.models.Event;
import az.edu.itbrains.final_evenmaster.models.TicketPurchase;
import az.edu.itbrains.final_evenmaster.enums.TicketType;
import az.edu.itbrains.final_evenmaster.models.User;
import az.edu.itbrains.final_evenmaster.repositories.EventRepository;
import az.edu.itbrains.final_evenmaster.repositories.TicketPurchaseRepository;
import az.edu.itbrains.final_evenmaster.services.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketPurchaseRepository ticketPurchaseRepository;
    private final EventRepository eventRepository;

    @Override
    public TicketStatsDto getStatsForEvent(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        List<TicketPurchase> purchases = ticketPurchaseRepository.findByEvent(event);

        int vipCount = 0;
        int normalCount = 0;
        double vipRevenue = 0;
        double normalRevenue = 0;
        double totalRevenue = 0;

        for (TicketPurchase p : purchases) {
            double revenue = p.getQuantity() * p.getPricePerTicket();

            if (p.getType() == TicketType.VIP) {
                vipCount += p.getQuantity();
                vipRevenue += revenue;
            } else if (p.getType() == TicketType.STANDARD) {
                normalCount += p.getQuantity();
                normalRevenue += revenue;
            }

            totalRevenue += revenue;
        }

        return new TicketStatsDto(vipCount, normalCount, vipRevenue, normalRevenue, totalRevenue);
    }
    @Override
    public TicketStatsDto getSummaryForOrganizer(User organizer) {
        List<Event> events = eventRepository.findByOrganizer(organizer);

        int vipCount = 0;
        int normalCount = 0;
        double vipRevenue = 0;
        double normalRevenue = 0;
        double totalRevenue = 0;

        for (Event event : events) {
            List<TicketPurchase> purchases = ticketPurchaseRepository.findByEvent(event);
            for (TicketPurchase p : purchases) {
                double revenue = p.getQuantity() * p.getPricePerTicket();
                if (p.getType() == TicketType.VIP) {
                    vipCount += p.getQuantity();
                    vipRevenue += revenue;
                } else if (p.getType() == TicketType.STANDARD) {
                    normalCount += p.getQuantity();
                    normalRevenue += revenue;
                }
                totalRevenue += revenue;
            }
        }

        return new TicketStatsDto(vipCount, normalCount, vipRevenue, normalRevenue, totalRevenue);
    }
    @Override
    public void buyTicket(User user, Long eventId, String type, int quantity) {
        Event event = eventRepository.findById(eventId).orElseThrow();
        TicketPurchase purchase = new TicketPurchase();

        purchase.setUser(user);
        purchase.setEvent(event);
        purchase.setType(TicketType.valueOf(type));
        purchase.setQuantity(quantity);

        double price = type.equals("VIP") ? event.getPriceVip() : event.getPriceStandard();
        purchase.setPricePerTicket(price);

        ticketPurchaseRepository.save(purchase);
    }
}