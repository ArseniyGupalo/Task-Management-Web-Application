package com.example.task_manegement_web_application.services;

import com.example.task_manegement_web_application.entity.ERole;
import com.example.task_manegement_web_application.entity.UserEntity;
import com.example.task_manegement_web_application.interfaces.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor

public class UserService {
    private final UserRepository userRepository;
    private BCryptPasswordEncoder encoder () {
        return new BCryptPasswordEncoder();
    }
    public UserEntity saveUser(UserEntity userEntity) {
        userEntity.setPassword(encoder().encode(userEntity.getPassword()));
        return userRepository.save(userEntity);
    }

    public UserEntity getUser(String username) {
        return userRepository.findByUsername(username);
    }

    public List<UserEntity> getAllUsers() {return userRepository.findAll();}

    public UserEntity getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }


    public void updateUserRole(Long userId, ERole role) {
        Optional<UserEntity> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            user.setERole(role);
            userRepository.save(user);
        } else {
            System.out.println("User with ID" + userId + " not found");
        }

    }


}
