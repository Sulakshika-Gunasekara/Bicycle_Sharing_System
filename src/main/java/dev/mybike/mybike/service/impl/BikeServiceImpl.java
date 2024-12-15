package dev.mybike.mybike.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import dev.mybike.mybike.service.BikeService;
import dev.mybike.mybike.model.Bike;
import dev.mybike.mybike.repository.BikeRepository;

@Service
public class BikeServiceImpl implements BikeService {

    @Autowired
    private BikeRepository bikeRepository;

    @Override
    public Bike reportBikeIssue(String bikeId, String issueType, String description){
        Bike bike = bikeRepository.findById(bikeId)
        .orElseThrow(() -> new IllegalArgumentException("Bike not found!"));
        bike.setCondition("Issue: " + issueType + ", Description: " + description);
        return bikeRepository.save(bike);
    }

    @Override
    public Bike reserveBike(String bikeId, String stationId, int duration) {
        Bike bike = bikeRepository.findById(bikeId)
                .orElseThrow(() -> new IllegalArgumentException("Bike not found!"));
        if (!bike.isAvailable()) {
            throw new IllegalArgumentException("Bike is already reserved.");
        }
        bike.setAvailable(false);
        return bikeRepository.save(bike);
    }

    @Override
    public Bike trackBike(String bikeId) {
        return bikeRepository.findById(bikeId)
                .orElseThrow(() -> new IllegalArgumentException("Bike not found!"));
    }

    @Override
    public List<Bike> getAvailableBikes(String stationId) {
        return bikeRepository.findByStationId(stationId);
    }




}
