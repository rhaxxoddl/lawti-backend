package com.pivot.hp.hometownpolitician.service;


import com.pivot.hp.hometownpolitician.entity.User;
import com.pivot.hp.hometownpolitician.exception.CustomException;
import com.pivot.hp.hometownpolitician.repository.UserRepository;
import com.pivot.hp.hometownpolitician.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final JwtTokenProvider jwtTokenProvider;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;


    public String signup(User user) {
        if (!userRepository.existsByIdentifier(user.getEmail())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return jwtTokenProvider.createToken(user.getEmail(), user.getRole());
        } else {
            throw new CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public String login(String email, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            return jwtTokenProvider.createToken(email, userRepository.findByIdentifier(email).getRole());
        } catch (AuthenticationException e) {
            throw new CustomException("Invalid username/password suppplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}
