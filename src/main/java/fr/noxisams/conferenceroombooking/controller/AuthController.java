package fr.noxisams.conferenceroombooking.controller;

import fr.noxisams.conferenceroombooking.dto.AuthResponse;
import fr.noxisams.conferenceroombooking.dto.LoginRequest;
import fr.noxisams.conferenceroombooking.dto.MessageResponse;
import fr.noxisams.conferenceroombooking.dto.SignupRequest;
import fr.noxisams.conferenceroombooking.service.authentication.AuthenticateUserService;
import fr.noxisams.conferenceroombooking.service.authentication.RegisterUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/conference-room-booking/auth")
public class AuthController {

    private final AuthenticateUserService authenticateUserService;
    private final RegisterUserService registerUserService;

    @Autowired
    public AuthController(AuthenticateUserService authenticateUserService,
                          RegisterUserService registerUserService) {
        Assert.notNull(authenticateUserService, "passwordEncoder must not be null");
        this.authenticateUserService = authenticateUserService;
        Assert.notNull(registerUserService, "registerUserService must not be null");
        this.registerUserService = registerUserService;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticate(@Valid @RequestBody LoginRequest loginRequest) {
        AuthResponse authResponse = authenticateUserService.authenticateUser(loginRequest);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> register(@Valid @RequestBody SignupRequest signUpRequest) {
        registerUserService.registerUser(signUpRequest);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
