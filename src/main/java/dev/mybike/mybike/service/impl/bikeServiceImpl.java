package dev.mybike.mybike.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.mybike.mybike.service.bikeService;
import dev.mybike.mybike.model.DockingStation;
import dev.mybike.mybike.model.bike;
import dev.mybike.mybike.repository.DockingStationRepository;
import dev.mybike.mybike.repository.bikeRepository;

@Service
public class bikeServiceImpl implements bikeService {

   @Autowired
    private bikeRepository bikeRepository;

    @Autowired
    private DockingStationRepository dockingStationRepository;

    @Override
    public List<bike> getAllBikes() {
        return bikeRepository.findAll();
    }

    

   @Override
public bike trackBike(String bikeId) {
    bike bike = bikeRepository.findById(bikeId)
            .orElseThrow(() -> new IllegalArgumentException("Bike not found."));
    // Add tracking logic here
    return bike;
}

@Override
public bike reportIssueBike(String bikeId, String issue) {
    bike bike = bikeRepository.findById(bikeId)
            .orElseThrow(() -> new IllegalArgumentException("Bike not found."));
    // Add issue reporting logic here
    bike.setCondition("Issue: " + issue);
    return bikeRepository.save(bike);
}

@Override
public bike reserveBike(String bikeId){
    bike bike = bikeRepository.findById(bikeId)
    .orElseThrow(() -> new IllegalArgumentException("Bike not found!"));
if (!bike.isAvailable()) {
throw new IllegalArgumentException("Bike is already reserved.");
}
bike.setAvailable(false);
return bikeRepository.save(bike);
}

@Override
public List<bike> getBikeByIsAvailable(Boolean isAvailable) {
    return bikeRepository.findByIsAvailable(isAvailable);
}

@Transactional
@Override
public bike updateBikeStation(String bikeId, String oldStationId, String newStationId) {
    bike bike = bikeRepository.findById(bikeId)
            .orElseThrow(() -> new RuntimeException("Bike not found."));
    
    //update new staiton available bikes
    //update old station empty docks
    //update new station empty docks
    //update old station available bikes
    //update bike station id
    DockingStation oldDockingStation = dockingStationRepository.findById(oldStationId)
            .orElseThrow(() -> new RuntimeException("Old Docking Station not found."));
         
    oldDockingStation.setEmptyDocks(oldDockingStation.getEmptyDocks() + 1);
    oldDockingStation.setAvailableBikes(oldDockingStation.getAvailableBikes() - 1);
    dockingStationRepository.save(oldDockingStation);
    
    DockingStation newDockingStation = dockingStationRepository.findById(newStationId)
            .orElseThrow(() -> new RuntimeException("New Docking Station not found."));
    newDockingStation.setEmptyDocks(newDockingStation.getEmptyDocks() - 1);
    newDockingStation.setAvailableBikes(newDockingStation.getAvailableBikes() + 1);
    dockingStationRepository.save(newDockingStation);
    
    bike.setStationId(newStationId);
    return bikeRepository.save(bike);
}

//comment

}
