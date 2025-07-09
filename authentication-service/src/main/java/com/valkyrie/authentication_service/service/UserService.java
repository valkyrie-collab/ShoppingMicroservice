package com.valkyrie.authentication_service.service;

import com.valkyrie.authentication_service.config.WebTokenConfig;
import com.valkyrie.authentication_service.model.Store;
import com.valkyrie.authentication_service.model.User;
import com.valkyrie.authentication_service.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder(12);
    private UserRepo repo;
    @Autowired
    private void setRepo(UserRepo repo) {this.repo = repo;}

    private AuthenticationManager authenticationManager;
    @Autowired
    private void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    private WebTokenConfig config;
    @Autowired
    private void setConfig(WebTokenConfig config) {this.config = config;}

    public Store<String> signIn(User user) {
        user = user.setPassword(ENCODER.encode(user.getPassword()));
        repo.save(user);
        user = repo.findById(user.getUsername()).orElse(null);
        return user == null? Store.initialize(HttpStatus.BAD_REQUEST, "Problem in sign-in") :
                Store.initialize(HttpStatus.OK, "Sign-In Successful");
    }

    public Store<String> logIn(User user) {
        String token = null;
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );

        if (authentication.isAuthenticated()) {
            token = config.generateToken(user.getUsername());
            return Store.initialize(HttpStatus.ACCEPTED, token);
        }

        return Store.initialize(HttpStatus.BAD_REQUEST, "Log-In failed :(");
    }

    public Store<User> getUser(String username) {
        return Store.initialize(HttpStatus.ACCEPTED, repo.findById(username).orElse(null));
    }
}
