package com.valkyrie.api_gateway.config;

import com.valkyrie.api_gateway.security.WebTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    private WebTokenFilter filter;
    @Autowired
    private void setFilter(WebTokenFilter filter) {this.filter = filter;}

//    private CustomUserDetailsService service;
//    @Autowired
//    private void setService(CustomUserDetailsService service) {this.service = service;}

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Throwable {
        return security.csrf(c -> c.disable())
                .authorizeHttpRequests(a -> a.requestMatchers(
                        "/user/sign-in", "/user/log-in").permitAll().anyRequest().authenticated()
                ).httpBasic(Customizer.withDefaults())
                .sessionManagement(s ->
                        s.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                ).addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class).build();
    }

//    @Bean
//    public BCryptPasswordEncoder encoder() {
//        return new BCryptPasswordEncoder(12);
//    }
//
//    @Bean
//    public CustomAuthenticationProvider authenticationProvider() {
//        return CustomAuthenticationProvider.initialize(service, encoder());
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(
//            AuthenticationConfiguration configuration) throws Exception {
//        return configuration.getAuthenticationManager();
//    }
}
