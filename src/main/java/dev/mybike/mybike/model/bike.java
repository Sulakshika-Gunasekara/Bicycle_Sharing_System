package dev.mybike.mybike.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;


@Document(collection  = "bike")
@Data
public class bike {

    @Id
    private String id;
    private String bikeId;
    private String stationId; 
    private boolean isAvailable;
    private String currentLocationLatitude;
    private String currentLocationLongitude;
   private String condition; //Good, Needs repair...
   
      
    

}
