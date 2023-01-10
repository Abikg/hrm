/*
package com.book.validation;

import com.book.model.CreateUserRequest;
import com.book.model.PasswordChangeRequestDTO;
import com.book.repository.UserRepository;
import com.book.utils.AuthenticationUtils;
import com.book.utils.ParseUtils;
import com.book.validation.error.UserError;
import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.EmailValidator;
import org.passay.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserValidation {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserError onRegistration(CreateUserRequest dto){

        synchronized (this.getClass()) {
            boolean isValid = true;
            CreateUserRequest errors = new CreateUserRequest();

            errors.setUsername(validateUserName(dto.getUsername()));
            isValid = isValid && errors.getUsername().isEmpty();

            errors.setPassword(validatePassword(dto.getPassword()));
            isValid = isValid && errors.getPassword().isEmpty();

            if (!dto.getPassword().equals(dto.getConfirmPassword())) {
                isValid = false;
                errors.setConfirmPassword("Confirm password did not match");
            }

            UserError error = new UserError();
            error.isValid = isValid;
            error.errors = errors;

            return error;
        }
    }

    public String onChangePassword(PasswordChangeRequestDTO passwordChangeRequestDTO){
        String error = validatePassword(passwordChangeRequestDTO.getNewPassword());
        if (ParseUtils.isNotNull(error)){
            return error;
        }

        if (!passwordChangeRequestDTO.getNewPassword().equals(passwordChangeRequestDTO.getConfirmPassword())){
            return "Password did not matched with confirm password";
        }

        String prevPassword = userRepository.findByUsername(AuthenticationUtils.getCurrentUser().getUsername()).get().getPassword();
        if (!passwordEncoder.matches(passwordChangeRequestDTO.getOldPassword(), prevPassword)){
            return "Your old password did not matched";
        }

        return "";
    }

    public String validateUserName(String username){
        if (username == null || username.trim().isEmpty()){
            return "Please provide the username";
        }

        if (!EmailValidator.getInstance()
                .isValid(username)){
            return "Please provide the valid email";
        }

        if (userRepository.findByUsername(username.trim()).isPresent()){
            return "Provide email is already registered";
        }

        return "";
    }

    public String validatePassword(String password){
        if (password == null || password.trim().isEmpty()){
            return "Please provide the password";
        }

        CharacterCharacteristicsRule characterCharacteristicsRule = new CharacterCharacteristicsRule(
                3,
                new CharacterRule(EnglishCharacterData.LowerCase, 2),
                new CharacterRule(EnglishCharacterData.UpperCase, 2),
                new CharacterRule(EnglishCharacterData.Digit),
                new CharacterRule(EnglishCharacterData.Special)
        );
        PasswordValidator validator = new PasswordValidator(List.of(
                new LengthRule(8, 30),
                characterCharacteristicsRule,
                new WhitespaceRule()));

        RuleResult validate = validator.validate(new PasswordData(password));

        if (!validate.isValid()){
            return "Please set the strong password";
        }

        return "";
    }

}
*/
