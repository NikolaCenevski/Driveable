package com.wp.driveable.driveableapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class DriveableApiApplication {

    @Bean
    public static PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
    public static void main(String[] args) {
        SpringApplication.run(DriveableApiApplication.class, args);
    }

}
