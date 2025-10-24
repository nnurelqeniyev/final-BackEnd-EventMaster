// UserService.java
package az.edu.itbrains.final_evenmaster.services;

import az.edu.itbrains.final_evenmaster.models.User;
import java.security.Principal;

public interface UserService {
    Long getCurrentUserId(Principal principal);
    String getName(Principal principal);
    User getCurrentUser(Principal principal);
}