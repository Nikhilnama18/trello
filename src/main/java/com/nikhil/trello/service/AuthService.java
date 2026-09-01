package com.nikhil.trello.service;

import com.nikhil.trello.dto.*;
import com.nikhil.trello.entity.User;
import com.nikhil.trello.exception.EmailAlreadyExistsException;
import com.nikhil.trello.exception.InvalidCredentialsException;
import com.nikhil.trello.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public SignupResponse signup(SignupRequest request){
        if(userRepository.existsByEmail(request.email())){

            throw new EmailAlreadyExistsException("Email already exists");
        }

        String encodedPassword = passwordEncoder.encode(request.password());
        User user = new User(request.name(), request.email(), encodedPassword);

        User savedUser = userRepository.save(user);

        GeneratedToken token = tokenService.generateAccessToken(savedUser);

        return new SignupResponse(savedUser.getId(), savedUser.getName(), savedUser.getEmail(), token.accessToken());
    }

    public LoginResponse login(LoginRequest request){
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(()-> new InvalidCredentialsException("Invalid email or password"));

        if(!passwordEncoder.matches(request.password(), user.getPassword())){
            throw new InvalidCredentialsException("Invalid email or password");
        }

        GeneratedToken token = tokenService.generateAccessToken(user);

        return new LoginResponse(user.getId(), token.accessToken(), token.expiration());
    }
}
