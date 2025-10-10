package az.edu.itbrains.final_evenmaster.dtos.event;

import az.edu.itbrains.final_evenmaster.models.Company;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {
    private String title;
    private String description;
    private String location;
    private Date dateLine;
    private Company company;
    private String status;
    private String image;
    private Double priceStandard;
    private Double priceVip;
}
