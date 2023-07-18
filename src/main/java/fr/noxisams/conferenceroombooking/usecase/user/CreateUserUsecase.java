package fr.noxisams.conferenceroombooking.usecase.user;

import fr.noxisams.conferenceroombooking.model.User;
import fr.noxisams.conferenceroombooking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Component
public class CreateUserUsecase {

    private final UserRepository userRepository;

    @Autowired
    public CreateUserUsecase(UserRepository userRepository) {
        Assert.notNull(userRepository, "userRepository must not be null");
        this.userRepository = userRepository;
    }

    @Transactional
    public User execute(User user) {
        return userRepository.save(user);
    }
}
