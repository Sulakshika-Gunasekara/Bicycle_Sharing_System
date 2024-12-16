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
    
    Bike reportBikeIssue(String bikeId, String issueType, String description);

    Bike trackBike(String bikeId);

    Bike reserveBike(String bikeId, String statoinId, int duration);

    List<Bike> getAvailableBikes(String stationId);


}
