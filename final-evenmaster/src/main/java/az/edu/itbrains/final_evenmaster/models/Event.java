package az.edu.itbrains.final_evenmaster.models;

import az.edu.itbrains.final_evenmaster.enums.EventStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @Column(length = 700)
    private String description;
    private String location;
    private LocalDate dateLine;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EventStatus status;
    private String image;
    private Double priceStandard;
    private Double priceVip;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
    @ManyToOne
    @JoinColumn(name = "organizer_id")
    private User organizer;
}
