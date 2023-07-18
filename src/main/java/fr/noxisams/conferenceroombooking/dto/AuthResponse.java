package fr.noxisams.conferenceroombooking.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class AuthResponse {

  private String token;
  private String type = "Bearer";
  private Long id;
  private String username;
  private List<String> roles;
}
