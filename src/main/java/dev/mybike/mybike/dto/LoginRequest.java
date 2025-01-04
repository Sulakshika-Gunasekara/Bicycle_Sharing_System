package dev.mybike.mybike.dto;

public class LoginRequest {
    private String Ridername;
    private String password;

    public LoginRequest() {
    }

    public LoginRequest(String Ridername, String password) {
        this.Ridername = Ridername;
        this.password = password;
    }

    public String getRidername() {
        return Ridername;
    }

    public void setRidername(String Ridername) {
        this.Ridername = Ridername;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
