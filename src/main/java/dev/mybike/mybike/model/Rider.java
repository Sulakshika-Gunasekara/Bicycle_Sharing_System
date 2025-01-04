package dev.mybike.mybike.model;


import java.util.Set;
import java.time.LocalDateTime;
import java.util.HashSet;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Document(collection = "riders")
public class Rider {
    @Id
    private String id;

    @NotBlank
    @Size(max = 20)
    private String ridername;

    @NotBlank
    @Size(max = 100)
    private String password;

    @NotBlank
    @Size(max = 20)
    private String firstName;

    @NotBlank
    @Size(max = 20)
    private String lastName;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(min = 10, max = 15)
    // @Pattern(regexp = "^\\+?[0-9. ()-]{10,15}$", message = "Invalid phone number")
    private String mobileNumber;
    
    private String nicImagePath;

    @NotBlank
    @Size(min = 16, max = 16)
    private String creditCardDetails;

    private double walletBalance;
    private String location;
    
    @DBRef
    private Set<Role> roles = new HashSet<>();

    private boolean enabled;
    private String verificationCode;
    private LocalDateTime verificationCodeExpiresAt;

    public Rider() {
    }

    public Rider(String ridername, String email, String password) {
        this.ridername = ridername;
        this.email = email;
        this.password = password;

    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public LocalDateTime getVerificationCodeExpiresAt() {
        return verificationCodeExpiresAt;
    }

    public void setVerificationCodeExpiresAt(LocalDateTime verificationCodeExpiresAt) {
        this.verificationCodeExpiresAt = verificationCodeExpiresAt;
    }

}