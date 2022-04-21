package com.wp.driveable.driveableapi.controller;

import com.wp.driveable.driveableapi.dto.Response.JwtResponse;
import com.wp.driveable.driveableapi.dto.requests.AuthRequest;
import com.wp.driveable.driveableapi.dto.requests.RegisterRequest;
import com.wp.driveable.driveableapi.entity.User;
import com.wp.driveable.driveableapi.service.UserService;
import com.wp.driveable.driveableapi.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    public AuthController(JwtUtil jwtUtil, AuthenticationManager authenticationManager, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<JwtResponse> generateToken(@RequestBody AuthRequest authRequest) throws Exception {
            User user= (User) authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword())
            ).getPrincipal();
            String token=jwtUtil.generateToken(authRequest.getUserName());
        return ResponseEntity.ok(new JwtResponse(token,user.getId(),user.getName(),user.getSurname(),user.getUsername(),user.getEmail(),user.getUserRole()));
    }
    @PostMapping("/register")
    public void registerUser(@RequestBody RegisterRequest registerRequest) throws Exception {
        userService.addUser(registerRequest);
    }
}
