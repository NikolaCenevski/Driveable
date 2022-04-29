package com.wp.driveable.driveableapi.config;

import com.wp.driveable.driveableapi.entity.UserRole;
import com.wp.driveable.driveableapi.filter.JwtFilter;
import com.wp.driveable.driveableapi.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserService userService;
    private final JwtFilter jwtFilter;
    private final PasswordEncoder passwordEncoder;
    public SecurityConfig(UserService userService, JwtFilter jwtFilter, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtFilter = jwtFilter;
        this.passwordEncoder = passwordEncoder;
    }
    String[] publicMatchers={"/api/auth/**", "/api/posts/**"};

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }

    @Bean()
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests().antMatchers(publicMatchers)
                .permitAll()
                .antMatchers("/api/moderator/**").hasAuthority(UserRole.MODERATOR.name()).anyRequest().authenticated()
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);;
    }
}