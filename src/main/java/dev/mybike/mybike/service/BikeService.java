package dev.mybike.mybike.service;

import dev.mybike.mybike.model.Bike;
import java.util.List;

/**
 * Interface for handling bike-related functionalities in a bike-sharing system.
 * This service provides operations for managing and interacting with bikes,
 * including reporting issues, tracking bike status, reserving bikes,
 * and retrieving availability information.
 */
public interface BikeService {

    List<Bike> getAllBikes();

    Bike getBikeById(String bikeId);

    // List<bike> getBikeByStationName(String stationName);

    Bike trackBike(String bikeId);

    Bike reportIssueBike(String bikeId, String issue);

    Bike reserveBike(String bikeId, String oldStationId); // correctly run when only isAvailable is true. otherwise 500
                                                          // error

    Bike returnBike(String bikeId, String newStationId);

    List<Bike> getBikeByIsAvailable(Boolean isAvailabile);

    Bike updateBikeStation(String bikeId, String oldStationId, String newStationId);

    List<Bike> getBikeByStationId(String stationId);

}
