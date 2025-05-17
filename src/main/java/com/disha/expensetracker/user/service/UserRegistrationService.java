package com.disha.expensetracker.user.service;

import org.apache.coyote.BadRequestException;

import com.disha.expensetracker.user.data.CreateUserRequest;

public interface UserRegistrationService {

    void registerUser(CreateUserRequest user) throws BadRequestException;
}
