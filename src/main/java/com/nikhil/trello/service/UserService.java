package com.nikhil.trello.service;

import com.nikhil.trello.entity.User;
import com.nikhil.trello.exception.UserNotFoundException;
import com.nikhil.trello.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public User updateUserName(UUID id, String name){
        User user = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User not found"));
        user.updateName(name);

        return userRepository.save(user);
    }

    public User getUserById(UUID id){
        return userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User not found"));
    }
}
