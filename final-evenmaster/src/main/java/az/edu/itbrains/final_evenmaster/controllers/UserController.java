package az.edu.itbrains.final_evenmaster.controllers;

import az.edu.itbrains.final_evenmaster.dtos.event.EventDto;
import az.edu.itbrains.final_evenmaster.dtos.user.UserDto;
import az.edu.itbrains.final_evenmaster.models.Company;
import az.edu.itbrains.final_evenmaster.models.Role;
import az.edu.itbrains.final_evenmaster.models.User;
import az.edu.itbrains.final_evenmaster.repositories.CompanyRepository;
import az.edu.itbrains.final_evenmaster.repositories.RoleRepository;
import az.edu.itbrains.final_evenmaster.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;
    @GetMapping("/register")
    public String register(){
        return "register.html";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute UserDto userDto, Model model) {
        User user = new User();
        user.setFullName(userDto.getFullName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        String roleName = "ROLE_" + userDto.getRole().getName().toUpperCase();
        Role role = roleRepository.findByName(roleName);
        user.setRoles(List.of(role));
        if (role == null) {
            throw new IllegalArgumentException("Belə rol mövcud deyil: " + roleName);
        }
        user.getRoles().add(role);
        if (userDto.getRole().getName().equalsIgnoreCase("ROLE_ORGANIZER")) {
            Company company = new Company();
            company.setName(user.getFullName());
            company.setDescription("Avtomatik yaradılıb");
            companyRepository.save(company);

            user.setCompany(company);
        }
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            model.addAttribute("emailError", "Bu email artıq istifadə olunub.");
            return "register";
        }


        userRepository.save(user);
        return "redirect:/login";
    }
}
