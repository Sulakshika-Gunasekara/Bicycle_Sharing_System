package dev.mybike.mybike.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

/**
 * Represents a docking station in the bike-sharing system.
 * This class is used to manage information about docking stations, including
 * their location, status, availability of bikes, and empty docks.
 * It also tracks whether a station is active or not.
 *
 * Fields:
 * - id: The unique identifier for the docking station in the database.
 * - stationId: The unique identifier used for the station in the bike-sharing
 * system.
 * - name: The name of the docking station.
 * - latitude: The latitude of the docking station's location.
 * - longitude: The longitude of the docking station's location.
 * - availableBikes: The number of bikes currently available at the docking
 * station.
 * - emptyDocks: The number of empty slots available for docking bikes at the
 * station.
 * - isActive: Indicates whether the docking station is currently active in the
 * system.
 */
@Data
@Document(collection = "docking_station")
public class DockingStation {

    @Id
    private String _id;
    private String address;
    private String name;
    private double latitude;
    private double longitude;
    private int availableBikes;
    private int emptyDocks;
    private boolean isActive;

}
