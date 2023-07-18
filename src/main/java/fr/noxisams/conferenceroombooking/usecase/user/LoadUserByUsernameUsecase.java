package fr.noxisams.conferenceroombooking.usecase.user;

import fr.noxisams.conferenceroombooking.exception.UserNotFoundException;
import fr.noxisams.conferenceroombooking.model.User;
import fr.noxisams.conferenceroombooking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Component
public class LoadUserByUsernameUsecase {

    private final UserRepository userRepository;

    @Autowired
    public LoadUserByUsernameUsecase(UserRepository userRepository) {
        Assert.notNull(userRepository, "userRepository must not be null");
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public User execute(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException("User Not Found with username: " + username)
        );
    }
}
