package com.wp.driveable.driveableapi.service;

import com.wp.driveable.driveableapi.dto.requests.AuthRequest;
import com.wp.driveable.driveableapi.dto.requests.RegisterRequest;
import com.wp.driveable.driveableapi.entity.User;
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
        User user=userRepository.findByUsername(username);
        return user;
    }
    public void addUser(RegisterRequest registerRequest)
    {
        User user=new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEmail(registerRequest.getEmail());
        user.setName(registerRequest.getName());
        user.setSurname(registerRequest.getSurname());
        user.setPhoneNumber(registerRequest.getPhoneNumber());
        userRepository.save(user);
    }

    public void editPhoneNumber(String number) {
        User user= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.setPhoneNumber(number);
        userRepository.save(user);
    }
    public void editMail(String mail) {
        User user= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.setEmail(mail);
        userRepository.save(user);
    }

    public void editPassword(String password) {
        User user= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }
}
