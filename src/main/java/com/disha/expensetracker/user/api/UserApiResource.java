package com.disha.expensetracker.user.api;

import org.apache.coyote.BadRequestException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.disha.expensetracker.user.data.CreateUserRequest;
import com.disha.expensetracker.user.service.UserRegistrationService;

@RestController
@RequestMapping("/users")
public class UserApiResource {

    private final UserRegistrationService userRegistrationService;

    public UserApiResource(UserRegistrationService userRegistrationService) {
        this.userRegistrationService = userRegistrationService;
    }

    @PostMapping("/register")
    public void registerUser(@RequestBody CreateUserRequest request) throws BadRequestException {
        this.userRegistrationService.registerUser(request);
    }

}
