package fr.noxisams.conferenceroombooking.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class SignupRequest {

  @NotBlank
  @Size(min = 2, max = 50)
  private String username;

  @Size(min = 2, max = 50)
  private String name;

  @Size(min = 2, max = 50)
  private String firsname;

  @NotBlank
  @Size(max = 50)
  @Email
  private String email;

  @NotBlank
  @Size(min = 6, max = 40)
  private String password;

  private Set<String> role;
}
