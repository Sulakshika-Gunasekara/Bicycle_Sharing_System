package dev.mybike.mybike.service;

import dev.mybike.mybike.model.bike;
import java.util.List;

public interface bikeService {
    
    List<bike> getAllBikes();

   bike trackBike(String bikeId); //getBikeById


   bike reportIssueBike(String bikeId, String issue);

   bike reserveBike(String bikeId);    //correctly run when only isAvailable is true. otherwise 500 curl error

   List<bike> getBikeByIsAvailable(Boolean isAvailabile);

   bike updateBikeStation(String bikeId, String oldStationId, String newStationId);
}



