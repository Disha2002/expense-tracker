package com.disha.expensetracker.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.disha.expensetracker.user.domain.CustomUserDetails;
import com.disha.expensetracker.user.domain.User;
import com.disha.expensetracker.user.domain.UserDetailsRepository;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserDetailsRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new CustomUserDetails(user);
    }

}
