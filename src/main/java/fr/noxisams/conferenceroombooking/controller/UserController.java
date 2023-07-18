package fr.noxisams.conferenceroombooking.controller;

import fr.noxisams.conferenceroombooking.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/conference-room-booking/user")
public class UserController {

    @GetMapping("/")
    public User get() {
        return new User(1L, "noxisams", "CHAMS", "Bruno", "test@mail.com", "password", new HashSet<>());
    }

    @GetMapping("/test")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public User getTest() {
        return new User(1L, "test", "TEST", "test", "test@mail.com", "test", new HashSet<>());
    }
}
