package com.samieteq.RegistrationSystem.security.auth;

import com.samieteq.RegistrationSystem.domain.dto.AuthenticationRequest;
import com.samieteq.RegistrationSystem.domain.dto.UserDTO;
import com.samieteq.RegistrationSystem.domain.model.User;
import com.samieteq.RegistrationSystem.domain.mapper.UserDTOMapper;
import com.samieteq.RegistrationSystem.email.EmailSender;
import com.samieteq.RegistrationSystem.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  private final UserDTOMapper userDTOMapper;
  //private final EmailSender emailSender;
  public ResponseEntity<?> authenticate(AuthenticationRequest request) {

    Authentication authenticate = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    request.username(),
                    request.password()
            )
    );

    User principal = (User) authenticate.getPrincipal();

    //TODO: create a mapper for user

    String token = jwtService.generateToken(principal);
    principal.setAccessToken(token);
    UserDTO userDTO = userDTOMapper.apply(principal);

    //emailSender.send("codewithsamie@gmail.com", adminEmail(principal.getFullName(), principal.getEmail()));


    return ResponseEntity.ok()
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
            .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Authorization")
            .body(userDTO);
  }
  private String adminEmail(String name, String email){
    return "<div style=\"font-weight: 600;\">" +  name + " has successfully login to registration system with " + email +  "</div>";
  }
}
