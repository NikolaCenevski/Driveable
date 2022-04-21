package com.wp.driveable.driveableapi.dto.Response;

import com.wp.driveable.driveableapi.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private Long id;
    private String name;
    private String surname;
    private String username;
    private String mail;
    private UserRole userRole;
}
