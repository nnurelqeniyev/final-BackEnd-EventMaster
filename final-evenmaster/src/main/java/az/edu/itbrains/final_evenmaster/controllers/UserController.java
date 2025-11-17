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
    public String register(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute UserDto userDto, Model model) {
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            model.addAttribute("emailError", "Bu email artıq istifadə olunub.");
            return "register";
        }

        String roleName = "ROLE_" + userDto.getRole().toUpperCase(); // "organizer" → "ROLE_ORGANIZER"
        Role role = roleRepository.findByName(roleName);
        if (role == null) {
            model.addAttribute("roleError", "Belə rol mövcud deyil: " + roleName);
            return "register";
        }

        User user = new User();
        user.setFullName(userDto.getFullName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRoles(List.of(role));

        if (roleName.equals("ROLE_ORGANIZER")) {
            Company company = new Company();
            company.setName(user.getFullName());
            company.setDescription("Avtomatik yaradılıb");
            companyRepository.save(company);
            user.setCompany(company);
        }

        userRepository.save(user);
        return "redirect:/login";
    }
}