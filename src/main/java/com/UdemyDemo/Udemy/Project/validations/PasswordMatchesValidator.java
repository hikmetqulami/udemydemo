package com.UdemyDemo.Udemy.Project.validations;

import com.UdemyDemo.Udemy.Project.annotations.PasswordMatches;
import com.UdemyDemo.Udemy.Project.payload.request.RegisterRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        RegisterRequest userSignUpRequest = (RegisterRequest) obj;
        return userSignUpRequest.getPassword().equals(userSignUpRequest.getConfirmPassword());
    }
}
