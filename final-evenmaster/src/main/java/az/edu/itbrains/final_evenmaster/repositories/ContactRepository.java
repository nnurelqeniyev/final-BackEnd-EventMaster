package az.edu.itbrains.final_evenmaster.repositories;

import az.edu.itbrains.final_evenmaster.models.Company;
import az.edu.itbrains.final_evenmaster.models.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
}
