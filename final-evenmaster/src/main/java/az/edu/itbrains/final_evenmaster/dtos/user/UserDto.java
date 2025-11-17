package az.edu.itbrains.final_evenmaster.dtos.user;

import az.edu.itbrains.final_evenmaster.models.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String fullName;
    private String email;
    private String password;
    private String role;
}
