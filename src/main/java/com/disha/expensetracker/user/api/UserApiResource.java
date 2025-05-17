package com.disha.expensetracker.user.api;

import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.disha.expensetracker.user.data.CreateUserRequest;
import com.disha.expensetracker.user.data.UserResponse;
import com.disha.expensetracker.user.service.UserRegistrationService;
import com.disha.expensetracker.user.service.UserService;

@RestController
@RequestMapping("api/users")
public class UserApiResource {

    private final UserRegistrationService userRegistrationService;
    private final UserService userService;

    public UserApiResource(UserRegistrationService userRegistrationService,
            UserService userService) {
        this.userRegistrationService = userRegistrationService;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody CreateUserRequest request) throws BadRequestException {
        this.userRegistrationService.registerUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getUserDetails(Authentication authentication) throws BadRequestException {
        UserResponse userResponse = this.userService.getCurrentUserDetails(authentication);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userResponse);
    }

}
