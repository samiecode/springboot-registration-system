package com.samieteq.RegistrationSystem.email;

//import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;
@Service
public class EmailValidation implements Predicate<String> {

    @Override
    public boolean test(String email) {
        //TODO: Regex to validate email
        //return EmailValidator.getInstance().isValid(email);
        return true;
    }
}
