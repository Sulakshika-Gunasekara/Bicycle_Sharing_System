package dev.mybike.mybike.dto;

public class LoginRequest {
    private String ridername;
    private String password;

    public LoginRequest() {
    }

    public LoginRequest(String ridername, String password) {
        this.ridername = ridername;
        this.password = password;
    }

    public String getRidername() {
        return ridername;
    }

    public void setRidername(String ridername) {
        this.ridername = ridername;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
