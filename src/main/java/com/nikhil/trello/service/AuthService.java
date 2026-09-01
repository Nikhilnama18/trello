package com.nikhil.trello.service;

import com.nikhil.trello.dto.SignUpResponse;
import com.nikhil.trello.dto.SignUpRequest;
import com.nikhil.trello.entity.User;
import com.nikhil.trello.exception.EmailAlreadyExistsException;
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

    public SignUpResponse signUp(SignUpRequest request){
        if(userRepository.existsByEmail(request.email())){

            throw new EmailAlreadyExistsException("Email already exists");
        }

        String encodedPassword = passwordEncoder.encode(request.password());
        User user = new User(request.name(), request.email(), encodedPassword);

        User savedUser = userRepository.save(user);

        String accessToken = tokenService.generateAccessToken(savedUser);

        return new SignUpResponse(savedUser.getId(), savedUser.getName(), savedUser.getEmail(), accessToken);
    }
}
