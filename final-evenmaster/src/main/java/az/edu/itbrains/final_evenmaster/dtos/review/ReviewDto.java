package az.edu.itbrains.final_evenmaster.dtos.review;

import lombok.Data;

@Data
public class ReviewDto {
    private Long id;
    private String userName;
    private int rating;
    private String comment;
    private boolean editable;
}