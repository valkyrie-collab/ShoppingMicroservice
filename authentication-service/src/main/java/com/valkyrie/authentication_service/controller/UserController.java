package com.valkyrie.authentication_service.controller;

import com.valkyrie.authentication_service.model.Store;
import com.valkyrie.authentication_service.model.User;
import com.valkyrie.authentication_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService service;
    @Autowired
    private void setService(UserService service) {this.service = service;}

    @PostMapping("/sign-in")
    public ResponseEntity<String> signIn(@RequestBody User user) {
        System.out.println(user.toString());
        Store<String> store = service.signIn(user);
        return ResponseEntity.status(store.getStatus()).body(store.getResponse());
    }

    @PostMapping("/log-in")
    public ResponseEntity<String> logIn(@RequestBody User user) {
        Store<String> store = service.logIn(user);
        return ResponseEntity.status(store.getStatus()).body(store.getResponse());
    }

    @GetMapping("/get-user")
    public ResponseEntity<User> getUser(@RequestParam String username) {
        Store<User> store = service.getUser(username);
        return ResponseEntity.status(store.getStatus()).body(store.getResponse());
    }

}
