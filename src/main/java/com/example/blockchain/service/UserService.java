package com.example.blockchain.service;

import com.example.blockchain.entity.User;
import com.example.blockchain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(String email, String rawPassword) {
        String encodedPassword = passwordEncoder.encode(rawPassword);
        User user = new User(email, encodedPassword);
        return userRepository.save(user);
    }

    public void updatePublicKey(String email, String publicKey) {
        // Fetch the user by email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        // Update the public key
        user.setPublicKey(publicKey);

        // Save the updated user back to the database
        userRepository.save(user);
    }

    public Optional<String> authenticateAndGetPublicKey(String email, String rawPassword) {
        // Fetch the user by email
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // Check if the provided password matches the stored password
            if (passwordEncoder.matches(rawPassword, user.getPassword())) {
                return Optional.ofNullable(user.getPublicKey());
            }
        }

        return Optional.empty();
    }
}
