package com.valkyrie.api_gateway.security;

import com.valkyrie.api_gateway.feign.AuthenticationFeignController;
import com.valkyrie.api_gateway.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    private AuthenticationFeignController feign;
    @Autowired
    private void setFeign(AuthenticationFeignController feign) {this.feign = feign;}

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = feign.getUser(username).getBody();

        if (user == null) {
            throw new UsernameNotFoundException("User 404");
        }

        return CustomUserDetails.initialize(user);
    }
}
