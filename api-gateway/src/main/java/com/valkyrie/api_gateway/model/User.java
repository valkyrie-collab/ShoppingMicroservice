package com.valkyrie.api_gateway.model;

public class User {
    private String username;
    private String password;

    public String getUsername() {return username;}

    public String getPassword() {return password;}

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
