package com.shabdhasethu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.shabdhasethu.dto.RegisterRequest;
import com.shabdhasethu.entity.User;
import com.shabdhasethu.repository.UserRepository;
import com.shabdhasethu.security.JwtUtil;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public String register(RegisterRequest request) {

        // Check email already exists
        if (userRepository.findByEmail(request.getEmail()) != null) {
            return "Email already exists";
        }

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());

        // Encrypt password
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);

        return "User Registered Successfully";
    }

    public String login(String email, String password) {

        User user = userRepository.findByEmail(email);

        if (user == null) {
            return "Invalid Credentials";
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            return "Invalid Credentials";
        }

        return jwtUtil.generateToken(user.getEmail());
    }

}