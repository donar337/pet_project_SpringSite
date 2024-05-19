package ru.itmo.wp.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.itmo.wp.form.RegisterCredentials;
import ru.itmo.wp.service.UserService;

@Component
public class RegisterCredentialsRegisterValidator implements Validator {
    private final UserService userService;

    public RegisterCredentialsRegisterValidator(UserService userService) {
        this.userService = userService;
    }

    public boolean supports(Class<?> clazz) {
        return RegisterCredentials.class.equals(clazz);
    }

    public void validate(Object target, Errors errors) {
        if (!errors.hasErrors()) {
            RegisterCredentials registerForm = (RegisterCredentials) target;
            if (userService.findByLogin(registerForm.getLogin()) != null) {
                errors.reject("invalid-login", "Login is already in use");
            }
        }
    }
}
