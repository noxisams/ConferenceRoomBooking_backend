package fr.noxisams.conferenceroombooking.usecase.user;

import fr.noxisams.conferenceroombooking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Component
public class CheckIfUserExistsByUsernameUsecase {

    private final UserRepository userRepository;

    @Autowired
    public CheckIfUserExistsByUsernameUsecase(UserRepository userRepository) {
        Assert.notNull(userRepository, "userRepository must not be null");
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public Boolean execute(String username) {
        return userRepository.existsByUsername(username);
    }
}
