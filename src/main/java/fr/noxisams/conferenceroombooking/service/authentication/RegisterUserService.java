package fr.noxisams.conferenceroombooking.service.authentication;

import fr.noxisams.conferenceroombooking.dto.SignupRequest;
import fr.noxisams.conferenceroombooking.enumeration.RoleEnum;
import fr.noxisams.conferenceroombooking.exception.UserAlreadyExistsException;
import fr.noxisams.conferenceroombooking.model.Role;
import fr.noxisams.conferenceroombooking.model.User;
import fr.noxisams.conferenceroombooking.usecase.role.LoadRoleByNameUsecase;
import fr.noxisams.conferenceroombooking.usecase.user.CheckIfUserExistsByUsernameUsecase;
import fr.noxisams.conferenceroombooking.usecase.user.CreateUserUsecase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.Set;

@Service
public class RegisterUserService {

    private final PasswordEncoder passwordEncoder;
    private final CreateUserUsecase createUserUsecase;
    private final LoadRoleByNameUsecase loadRoleByNameUsecase;
    private final CheckIfUserExistsByUsernameUsecase checkIfUserExistsByUsernameUsecase;

    @Autowired
    public RegisterUserService(PasswordEncoder passwordEncoder,
                               CreateUserUsecase createUserUsecase,
                               LoadRoleByNameUsecase loadRoleByNameUsecase,
                               CheckIfUserExistsByUsernameUsecase checkIfUserExistsByUsernameUsecase) {
        Assert.notNull(passwordEncoder, "passwordEncoder must not be null");
        this.passwordEncoder = passwordEncoder;
        Assert.notNull(createUserUsecase, "createUserUsecase must not be null");
        this.createUserUsecase = createUserUsecase;
        Assert.notNull(loadRoleByNameUsecase, "loadRoleByNameUsecase must not be null");
        this.loadRoleByNameUsecase = loadRoleByNameUsecase;
        Assert.notNull(checkIfUserExistsByUsernameUsecase, "checkIfUserExistsByUsernameUsecase must not be null");
        this.checkIfUserExistsByUsernameUsecase = checkIfUserExistsByUsernameUsecase;
    }

    public void registerUser(SignupRequest signUpRequest) {
        if (checkIfUserExistsByUsernameUsecase.execute(signUpRequest.getUsername())) {
            throw new UserAlreadyExistsException("User Already Exists with username: " + signUpRequest.getUsername());
        }

        User user = User.builder()
                .username(signUpRequest.getUsername())
                .name(signUpRequest.getName())
                .firstname(signUpRequest.getFirsname())
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .roles(new HashSet<>())
                .build();

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = loadRoleByNameUsecase.execute(RoleEnum.ROLE_USER);
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin" -> {
                        Role adminRole = loadRoleByNameUsecase.execute(RoleEnum.ROLE_ADMIN);
                        roles.add(adminRole);
                    }
                    case "mod" -> {
                        Role modRole = loadRoleByNameUsecase.execute(RoleEnum.ROLE_MODERATOR);
                        roles.add(modRole);
                    }
                    default -> {
                        Role userRole = loadRoleByNameUsecase.execute(RoleEnum.ROLE_USER);
                        roles.add(userRole);
                    }
                }
            });
        }
        user.setRoles(roles);

        createUserUsecase.execute(user);
    }
}
