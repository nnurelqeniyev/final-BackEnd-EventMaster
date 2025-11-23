package az.edu.itbrains.final_evenmaster.models;

import az.edu.itbrains.final_evenmaster.enums.TicketType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class TicketPurchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Event event;

    private int quantity;

    @Enumerated(EnumType.STRING)
    private TicketType type; // VIP or NORMAL

    private double pricePerTicket;

    private LocalDateTime purchaseTime;
}