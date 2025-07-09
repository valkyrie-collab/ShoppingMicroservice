package com.valkyrie.authentication_service.config;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private CustomUserDetailsService service;
    private BCryptPasswordEncoder encoder;

    private CustomAuthenticationProvider(CustomUserDetailsService service, BCryptPasswordEncoder encoder) {
        this.service = service; this.encoder = encoder;
    }

    public static CustomAuthenticationProvider initialize(
            CustomUserDetailsService service, BCryptPasswordEncoder encoder) {
        return new CustomAuthenticationProvider(service, encoder);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        UserDetails userDetails = service.loadUserByUsername(username);

        if (userDetails == null || !encoder.matches(password, userDetails.getPassword())) {
            throw new RuntimeException("User not matched");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
