package dev.mybike.mybike.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
/**
 * Represents a Rider in the bike-sharing system.
 *
 * This class contains information about a rider, including their
 * personal details, account credentials, and financial information.
 * It also includes attributes to store the rider's current balance
 * and location.
 *
 * Fields:
 * - id: The unique identifier for the rider in the database.
 * - username: The username of the rider used for authentication.
 * - password: The password of the rider used for authentication.
 * - firstName: The first name of the rider.
 * - lastName: The last name of the rider.
 * - email: The email address of the rider.
 * - mobileNumber: The mobile phone number of the rider.
 * - nicImagePath: Path to the uploaded image of the rider's NIC (National Identity Card).
 * - creditCardDetails: The rider's credit card details for payment purposes.
 * - balance: The current account balance of the rider.
 * - location: The current location of the rider.
 */
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
    private double balance;
    private String location; // Added location attribute
}
