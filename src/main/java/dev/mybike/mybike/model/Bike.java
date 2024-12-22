package dev.mybike.mybike.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

/**
 * Represents a bike in the bike-sharing system.
 * This class is used to manage bike-related information, including its availability,
 * current location, condition, and its association with a specific station.
 * It also includes an identifier for the bike and its unique ID in the system.

 * Fields:
 * - id: The unique identifier for the bike in the database.
 * - bikeId: The unique bike identifier used within the system.
 * - stationId: The identifier of the docking station to which the bike is assigned.
 * - isAvailable: Indicates whether the bike is currently available for use.
 * - currentLocationLatitude: The current latitude of the bike.
 * - currentLocationLongitude: The current longitude of the bike.
 * - condition: The current condition of the bike, specifying whether it is in good
 *   condition, requires repair, etc.
 */
@Document(collection = "bike")
@Data
public class Bike {

    @Id
    private String id;
    private String bikeId;
    private String stationId;
    private boolean isAvailable;
    private String currentLocationLatitude;
    private String currentLocationLongitude;
   private String condition; //Good, Needs repair...
   
      
    

}
