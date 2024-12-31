package dev.mybike.mybike.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "riders")
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
    private double points;
    private String location;
    private List<String> roles; // Add roles attribute

}