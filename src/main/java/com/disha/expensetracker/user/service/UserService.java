package com.disha.expensetracker.user.service;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.disha.expensetracker.user.data.UserResponse;
import com.disha.expensetracker.user.domain.CustomUserDetails;
import com.disha.expensetracker.user.domain.User;
import com.disha.expensetracker.user.domain.UserDetailsRepositoryWrapper;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserDetailsRepositoryWrapper userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        try {
            User user = userRepository.findByUserName(userName);
            return new CustomUserDetails(user);
        } catch (BadRequestException ex) {
            return null;
        }
    }

    public UserResponse getCurrentUserDetails(Authentication authentication) throws BadRequestException {
        String username = authentication.getName();
        User user = userRepository.findByUserName(username);
        return UserResponse.builder().email(user.getEmail()).userName(username).isActive(user.getIsActive()).build();
    }

}
