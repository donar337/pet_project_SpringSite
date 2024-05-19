package ru.itmo.wp.controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.itmo.wp.domain.MyUser;
import ru.itmo.wp.exception.ValidationException;
import ru.itmo.wp.form.RegisterCredentials;
import ru.itmo.wp.form.validator.RegisterCredentialsRegisterValidator;
import ru.itmo.wp.service.JwtService;
import ru.itmo.wp.service.UserService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/1")
public class UserController {
    private final JwtService jwtService;
    private final UserService userService;

    private final RegisterCredentialsRegisterValidator registerCredentialsRegisterValidator;

    public UserController(JwtService jwtService, UserService userService, RegisterCredentialsRegisterValidator registerCredentialsRegisterValidator) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.registerCredentialsRegisterValidator = registerCredentialsRegisterValidator;
    }

    @GetMapping("users/auth")
    public MyUser findUserByJwt(@RequestParam String jwt) {
        return jwtService.find(jwt);
    }

    @GetMapping("users")
    public List<MyUser> getUsers() {
        return userService.findAll();
    }

    @GetMapping("users/by/id")
    public List<MyUser> getUsersById(@RequestParam List<Long> ids) {
        return userService.findAllById(new ArrayList<>());
    }

    @InitBinder("registerCredentials")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(registerCredentialsRegisterValidator);
    }

    @PostMapping("register")
    public void registerUser(@RequestBody @Valid RegisterCredentials registerCredentials,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }

        userService.save(registerCredentials.getLogin(), registerCredentials.getName(), registerCredentials.getPassword());
    }
}
