package az.edu.itbrains.final_evenmaster.repositories;

import az.edu.itbrains.final_evenmaster.models.Company;
import az.edu.itbrains.final_evenmaster.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
