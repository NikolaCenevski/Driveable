package com.wp.driveable.driveableapi.service;

import com.wp.driveable.driveableapi.dto.Response.MessageResponse;
import com.wp.driveable.driveableapi.dto.requests.RegisterRequest;
import com.wp.driveable.driveableapi.entity.User;
import com.wp.driveable.driveableapi.exceptions.BadRequestException;
import com.wp.driveable.driveableapi.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public MessageResponse addUser(RegisterRequest registerRequest) throws BadRequestException {

        if(userRepository.findByUsername(registerRequest.getUsername()) != null) {
            throw new BadRequestException("Username already exists");
        }

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEmail(registerRequest.getEmail());
        user.setName(registerRequest.getName());
        user.setSurname(registerRequest.getSurname());
        user.setPhoneNumber(registerRequest.getPhoneNumber());
        userRepository.save(user);
        return new MessageResponse("User registered successfully");
    }

    public MessageResponse editPhoneNumber(String number) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.setPhoneNumber(number);
        userRepository.save(user);
        return new MessageResponse("Phone number changed");
    }

    public MessageResponse editMail(String mail) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.setEmail(mail);
        userRepository.save(user);
        return new MessageResponse("Email changed");
    }

    public MessageResponse editPassword(String password) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        return new MessageResponse("Password changed");
    }
}
