package az.edu.itbrains.final_evenmaster.controllers;

import az.edu.itbrains.final_evenmaster.enums.EventStatus;
import az.edu.itbrains.final_evenmaster.repositories.EventRepository;
import az.edu.itbrains.final_evenmaster.services.EventService;
import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import az.edu.itbrains.final_evenmaster.models.Event;


import java.util.List;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index(){
        return "index.html";
    }
    @GetMapping("/features")
    public String features(){
        return "features.html";
    }
    @GetMapping("/event")
    public String event(){
        return "event.html";
    }
    @GetMapping("/contact")
    public String contact(){
        return "contact.html";
    }
    @GetMapping("/login")
    public String login(){
        return "login.html";
    }
}
