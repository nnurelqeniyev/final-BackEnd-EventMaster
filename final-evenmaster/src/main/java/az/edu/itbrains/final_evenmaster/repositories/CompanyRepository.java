package az.edu.itbrains.final_evenmaster.repositories;

import az.edu.itbrains.final_evenmaster.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
