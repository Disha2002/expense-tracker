package com.disha.expensetracker.user.domain;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class UserDetailsRepositoryWrapper {

    private final UserDetailsRepository userDetailsRepository;

    public UserDetailsRepositoryWrapper(UserDetailsRepository userDetailsRepository) {
        this.userDetailsRepository = userDetailsRepository;
    }

    public List<User> getAll() {
        return this.userDetailsRepository.findAll();
    }

    public User save(User userDetails) {
        return this.userDetailsRepository.save(userDetails);
    }

}
