package az.edu.itbrains.final_evenmaster.services.impls;

import az.edu.itbrains.final_evenmaster.dtos.user.UserDto;
import az.edu.itbrains.final_evenmaster.models.Company;
import az.edu.itbrains.final_evenmaster.models.Role;
import az.edu.itbrains.final_evenmaster.models.User;
import az.edu.itbrains.final_evenmaster.repositories.CompanyRepository;
import az.edu.itbrains.final_evenmaster.repositories.RoleRepository;
import az.edu.itbrains.final_evenmaster.repositories.UserRepository;
import az.edu.itbrains.final_evenmaster.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User getCurrentUser(Principal principal) {
        String email = principal.getName();
        return userRepository.findByEmail(email).orElseThrow();
    }

    @Override
    public Long getCurrentUserId(Principal principal) {
        String email = principal.getName(); // email-i al
        User user = userRepository.findByEmail(email).orElseThrow(); // user-i tap
        return user.getId();
    }

    @Override
    public String getName(Principal principal) {
        String username = principal.getName();
        User user = userRepository.findByEmail(username).orElseThrow();
        return user.getFullName(); // və ya user.getName()
    }
    public void registerUser(UserDto dto) {
        String roleName = "ROLE_" + dto.getRole().toUpperCase();
        Role role = roleRepository.findByName(roleName);
        Company company = null;
        if (roleName.equals("ROLE_ORGANIZER")) {
            company = new Company();
            company.setName(dto.getFullName());
            company.setDescription("Avtomatik yaradılıb");
            companyRepository.save(company);
        }
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setFullName(dto.getFullName());
        user.setCompany(company);
        user.setRoles(List.of(role));

        userRepository.save(user);
    }
}

