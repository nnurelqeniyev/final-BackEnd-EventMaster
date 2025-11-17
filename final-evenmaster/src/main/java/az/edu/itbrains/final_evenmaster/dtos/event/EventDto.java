package az.edu.itbrains.final_evenmaster.dtos.event;

import az.edu.itbrains.final_evenmaster.enums.EventStatus;
import az.edu.itbrains.final_evenmaster.models.Company;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {
    private Long id;
    private String title;
    private String description;
    private String location;
    private LocalDateTime dateLine;
    private EventStatus status;
    private String image;
    private Double priceStandard;
    private Double priceVip;
    private Double averageRating;
    private Integer reviewCount;

}
