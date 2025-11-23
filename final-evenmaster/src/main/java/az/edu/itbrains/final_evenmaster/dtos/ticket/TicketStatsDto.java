package az.edu.itbrains.final_evenmaster.dtos.ticket;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TicketStatsDto {
    private int vipCount;
    private int normalCount;
    private double vipRevenue;
    private double normalRevenue;
    private double totalRevenue;
}