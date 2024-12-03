package dev.mybike.mybike.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("riders")
public class Rider {
    @Id
    private String id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNumber;
    private String nicImagePath;
    private String creditCardDetails;
    private double balance;
    public Object getEmail() {
        return email;
    }
    public Object getName() {
        return null;
    }

    public void setName(Object name) {
    }

    public void setEmail(Object email) {
    }
}
