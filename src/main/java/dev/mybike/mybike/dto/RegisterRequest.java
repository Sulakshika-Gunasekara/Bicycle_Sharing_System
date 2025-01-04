package dev.mybike.mybike.dto;
import lombok.Data;

import java.util.Set;
@Data
public class RegisterRequest {
    private String Ridername;
    private String email;
    private String password;
    private Set<String> roles;

    public RegisterRequest() {
    }

    
}
