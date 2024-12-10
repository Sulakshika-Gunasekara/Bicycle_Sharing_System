package dev.mybike.mybike.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Data
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
    private double balance;
    //gdgd

}
