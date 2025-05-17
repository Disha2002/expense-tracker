package com.disha.expensetracker.user.api;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.disha.expensetracker.user.constant.JwtUtil;
import com.disha.expensetracker.user.data.LoginRequest;
import com.disha.expensetracker.user.data.LoginResponse;
import com.disha.expensetracker.user.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthApiResource {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) throws BadRequestException {
        try {
            var authToken = new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword());
            authenticationManager.authenticate(authToken);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }
        UserDetails userDetails = userService.loadUserByUsername(request.getUserName());
        String token = jwtUtil.generateToken(userDetails.getUsername());

        return new LoginResponse(token);
    }
}
