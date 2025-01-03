package dev.mybike.mybike.model;

import java.util.Date;

import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents a trip of one bike in the system.
 * This class is used to manage trip time duration information, including the
 * start and end time of a trip.
 * Main purpose for this class to calculate the trip duration and cost.
 */

@Document(collection = "trip")
@Data
public class Trip {

    @Id
    private String id;
    private Date startTime;
    private Date endTime;

}
