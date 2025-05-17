package com.disha.expensetracker.user.domain;

import java.util.List;
import java.util.Optional;

import org.apache.coyote.BadRequestException;
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

    public User findByUserName(String userName) throws BadRequestException {
        Optional<User> user = this.userDetailsRepository.findByUserName(userName);
        if (user.isEmpty()) {
            throw new BadRequestException("User doesn't exist");
        }
        return user.get();
    }

}
