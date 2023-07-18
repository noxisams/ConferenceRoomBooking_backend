package fr.noxisams.conferenceroombooking.security;

import fr.noxisams.conferenceroombooking.dto.UserAuthResponse;
import fr.noxisams.conferenceroombooking.model.User;
import fr.noxisams.conferenceroombooking.usecase.user.LoadUserByUsernameUsecase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class UserAuthService implements UserDetailsService {

  private final LoadUserByUsernameUsecase loadUserByUsernameUsecase;

  @Autowired
  public UserAuthService(LoadUserByUsernameUsecase loadUserByUsernameUsecase) {
    Assert.notNull(loadUserByUsernameUsecase, "loadUserByUsernameUsecase must not be null");
    this.loadUserByUsernameUsecase = loadUserByUsernameUsecase;
  }

  @Override
  public UserDetails loadUserByUsername(String username) {
    User user = loadUserByUsernameUsecase.execute(username);
    return UserAuthResponse.build(user);
  }
}
