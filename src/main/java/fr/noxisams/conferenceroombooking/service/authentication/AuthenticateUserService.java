package fr.noxisams.conferenceroombooking.service.authentication;

import fr.noxisams.conferenceroombooking.dto.AuthResponse;
import fr.noxisams.conferenceroombooking.dto.LoginRequest;
import fr.noxisams.conferenceroombooking.dto.UserAuthResponse;
import fr.noxisams.conferenceroombooking.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthenticateUserService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthenticateUserService(AuthenticationManager authenticationManager,
                                   JwtUtil jwtUtil) {
        Assert.notNull(authenticationManager, "authenticationManager must not be null");
        this.authenticationManager = authenticationManager;
        Assert.notNull(jwtUtil, "jwtUtil must not be null");
        this.jwtUtil = jwtUtil;
    }

    public AuthResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtil.generateJwtToken(authentication);

        UserAuthResponse userPrincipal = (UserAuthResponse) authentication.getPrincipal();
        List<String> roles = userPrincipal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return AuthResponse.builder()
                .token(jwt)
                .id(userPrincipal.getId())
                .username(userPrincipal.getUsername())
                .roles(roles)
                .build();
    }
}
