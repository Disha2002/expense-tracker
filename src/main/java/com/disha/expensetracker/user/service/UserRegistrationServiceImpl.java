package com.disha.expensetracker.user.service;

import java.util.List;

import org.apache.coyote.BadRequestException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.disha.expensetracker.user.data.CreateUserRequest;
import com.disha.expensetracker.user.domain.User;
import com.disha.expensetracker.user.domain.UserDetailsRepositoryWrapper;

@Service
public class UserRegistrationServiceImpl implements UserRegistrationService {

    private final UserDetailsRepositoryWrapper userDetailsRepositoryWrapper;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserRegistrationServiceImpl(UserDetailsRepositoryWrapper userDetailsRepositoryWrapper) {
        this.userDetailsRepositoryWrapper = userDetailsRepositoryWrapper;
    }

    @Override
    public void registerUser(CreateUserRequest user) throws BadRequestException {
        User validatedUser = validateUserDetails(user);
        this.userDetailsRepositoryWrapper.save(validatedUser);
    }

    private User validateUserDetails(CreateUserRequest user) throws BadRequestException {
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new BadRequestException("Email is required");
        }
        if (user.getUserName().isEmpty()) {
            throw new BadRequestException("user name is mandotory");
        }
        List<User> users = this.userDetailsRepositoryWrapper.getAll();
        boolean userExists = false;
        boolean emailExists = false;

        for (User existingUser : users) {
            if (existingUser.getUserName().equals(user.getUserName())) {
                userExists = true;
            }
            if (existingUser.getEmail().equals(user.getEmail())) {
                emailExists = true;
            }
            if (userExists && emailExists) {
                break;
            }
        }
        if (userExists) {
            throw new BadRequestException("User Name Already Exists");
        }
        if (emailExists) {
            throw new BadRequestException("Email already exists");
        }
        String encodedPassword = this.passwordEncoder.encode(user.getPassword());
        return User.builder().userName(user.getUserName()).password(encodedPassword).email(user.getEmail())
                .isActive(true).build();
    }
}