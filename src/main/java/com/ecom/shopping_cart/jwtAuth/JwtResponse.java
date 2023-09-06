package com.ecom.shopping_cart.jwtAuth;

public class JwtResponse {
    String token;
    private String username;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public JwtResponse(String token) {
        this.token = token;
    }
    public JwtResponse(String username, String token2) {
this.username=username;
this.token=token2;
    }
}
