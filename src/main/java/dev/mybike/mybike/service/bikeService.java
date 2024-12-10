package dev.mybike.mybike.service;

import dev.mybike.mybike.model.bike;
import java.util.List;

public interface bikeService {
    
    bike reportBikeIssue(String bikeId, String issueType, String description);

    bike trackBike(String bikeId);

    bike reserveBike(String bikeId, String statoinId, int duration);

    List<bike> getAvailableBikes(String stationId);


}
