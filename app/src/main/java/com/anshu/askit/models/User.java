package com.anshu.askit.models;
public class User {
    public String username;
    public String email;
    public String password;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    // Getters and setters (if needed)
}

