package com.samieteq.RegistrationSystem.service;

import com.samieteq.RegistrationSystem.data.AppUserRepository;
import com.samieteq.RegistrationSystem.domain.dto.RegistrationRequest;
import com.samieteq.RegistrationSystem.domain.dto.UserRole;
import com.samieteq.RegistrationSystem.domain.model.ConfirmationToken;
import com.samieteq.RegistrationSystem.domain.model.User;
import com.samieteq.RegistrationSystem.email.EmailSender;
import com.samieteq.RegistrationSystem.exception.DuplicateResourceException;
import com.samieteq.RegistrationSystem.security.jwt.JwtService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class AppUserService {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailSender emailSender;
    private final ConfirmationTokenService confirmationTokenService;
    private final JwtService jwtService;

    public ResponseEntity<?> registerCustomer(RegistrationRequest request){
        boolean userExists = appUserRepository.findByEmail(request.email())
                .isPresent();

        if (userExists){
            // TODO: check of attributes are the same and
            // TODO: if email not confirmed send confirmation email.
            throw new DuplicateResourceException("Email Already taken", HttpStatus.NOT_FOUND);
        }

        User user = User.builder()
                .fullName(request.fullName())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(UserRole.USER)
                .locked(false)
                .enabled(true)
                .build();

        appUserRepository.save(user);

        String token = UUID.randomUUID().toString();
        log.info("{}", token);
        ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(), LocalDateTime.now().plusHours(24), user);
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        String link = "http://localhost:8081/api/v1/customer/confirm?token=" + token;

        // I remove this email sender because is not a commercial site

        //emailSender.send(request.email(), buildEmail( request.fullName(), link));

        emailSender.send("codewithsamie@gmail.com", adminEmail(request.fullName(), request.email()));

        //TODO: If there is an error why sending the email to the register user, delete the user data from the database
        // And Throw an Error

        String jwtToken = jwtService.generateToken(user);
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Authorization")
                .body("Account Created Successfully!");
    }

    private String adminEmail(String name, String email){
        return "<div>" +  name + " has successfully register for registration system with " + email +  "</div";
    }

    private String buildEmail(String name, String link){
        return "<head>\n" +
                "<link rel=\"preconnect\" href=\"https://fonts.googleapis.com\" />\n" +
                "<link rel=\"preconnect\" href=\"https://fonts.gstatic.com\" crossorigin />\n" +
                "<link href=\"https://fonts.googleapis.com/css2?family=DM+Sans:wght@400;500;700&display=swap\" rel=\"stylesheet\" />\n" +
                "<style>* {margin: 0;box-sizing: border-box;font-family: \"DM Sans\", sans-serif;}</style>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div style=\"background: #3bb85d;display: flex;flex-direction: column;justify-content: center;align-items: center;font-family: 'Poppins', sans-serif;height: 90px;color: #fff;font-weight: 500;\">\n" +
                "<h1 style=\"font-weight: 600\">CardSale</h1>\n" +
                "        <p style=\"font-size: 12px\">The Better Way to Send & Receive Airtime.</p>\n" +
                "</div>\n" +
                "<div style=\"display: grid;grid-template-columns: repeat(12, minmax(0, 1fr));\">\n" +
                "<div style=\"grid-column-start: 2; grid-column-end: 12; background: #fff\">\n" +
                "<h1 style=\"color: #3bb85d; font-size: 24px; padding: 30px 0\">Dear " + name + "</h1>\n" +
                "<p style=\"font-size: 20px; font-weight: 400\">Thank you for registering. Please click on the below button to activate your account</p>\n" +
                "<a href=\"" + link + "\" style=\"display: block;font-size: 18px;font-weight: 700;color: #fff;padding: 20px 20px;background: #3bb85d;border-radius: 5px;margin-top: 40px;text-align: center;text-decoration: none;width: 100%;\">Confirm Email Address</a>\n" +
                "<p style=\"font-size: 20px; padding: 20px 0; font-weight: 400\">Link will Expire in 24 Hours</p>\n" +
                "<p style=\"font-size: 20px; font-weight: 400\">If you have any trouble clicking the button above, please copy and paste the URL below into your web browser.</p>\n" +
                "<a href=\"#\" style=\" display:block; color: #3bb85d; width: 100%; padding: 10px 0\">" + link + "</a>\n" +
                "</div>\n" +
                "</div>\n" +
                "</body>";
    }

    public String confirmToken(String token){
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token).orElseThrow(()-> new IllegalStateException("Token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);

        enableCustomerAccount(confirmationToken.getUser().getEmail());
        
        log.info("Account activated successfully with token ~ {}", token );
        return "confirmed";
    }

    private int enableCustomerAccount(String email){
        return appUserRepository.enableCustomerAccount(email);
    }



}
