package az.edu.itbrains.final_evenmaster.repositories;

import az.edu.itbrains.final_evenmaster.models.Event;
import az.edu.itbrains.final_evenmaster.models.TicketPurchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketPurchaseRepository extends JpaRepository<TicketPurchase, Long> {
    List<TicketPurchase> findByEvent(Event event);
}