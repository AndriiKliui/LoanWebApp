package com.finanse.dto;

import com.finanse.model.User;
import com.finanse.model.UserRole;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

public class SignupFormScreenDto {

    private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";
    private static final String EMAIL_MESSAGE = "{email.message}";

    @NotBlank(message = SignupFormScreenDto.NOT_BLANK_MESSAGE)
    @Email(message = SignupFormScreenDto.EMAIL_MESSAGE)
    private String email;

    @NotBlank(message = SignupFormScreenDto.NOT_BLANK_MESSAGE)
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User createAccount() {
        return new User(getEmail(), getPassword(), UserRole.USER);
    }
}
