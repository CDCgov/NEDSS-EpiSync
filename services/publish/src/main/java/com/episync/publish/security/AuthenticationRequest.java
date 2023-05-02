package com.episync.publish.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthenticationRequest {
    private String username;
    private String password;

    // Parameterized constructor
    public AuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}