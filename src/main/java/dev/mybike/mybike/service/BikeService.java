package dev.mybike.mybike.service;

import dev.mybike.mybike.model.Bike;
import java.util.List;

public interface BikeService {
    
    Bike reportBikeIssue(String bikeId, String issueType, String description);

    Bike trackBike(String bikeId);

    Bike reserveBike(String bikeId, String statoinId, int duration);

    List<Bike> getAvailableBikes(String stationId);


}
