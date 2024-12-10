package dev.mybike.mybike.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.mybike.mybike.service.bikeService;
import dev.mybike.mybike.model.bike;
import dev.mybike.mybike.repository.bikeRepository;

@Service
public class bikeServiceImpl implements bikeService {

    @Autowired
    private bikeRepository bikeRepository;

    @Override
    public bike reportBikeIssue(String bikeId, String issueType, String description){
        bike bike = bikeRepository.findById(bikeId)
        .orElseThrow(() -> new IllegalArgumentException("Bike not found!"));
        bike.setCondition("Issue: " + issueType + ", Description: " + description);
        return bikeRepository.save(bike);
    }

    @Override
    public bike reserveBike(String bikeId, String stationId, int duration) {
        bike bike = bikeRepository.findById(bikeId)
                .orElseThrow(() -> new IllegalArgumentException("Bike not found!"));
        if (!bike.isAvailable()) {
            throw new IllegalArgumentException("Bike is already reserved.");
        }
        bike.setAvailable(false);
        return bikeRepository.save(bike);
    }

    @Override
    public bike trackBike(String bikeId) {
        return bikeRepository.findById(bikeId)
                .orElseThrow(() -> new IllegalArgumentException("Bike not found!"));
    }

    @Override
    public List<bike> getAvailableBikes(String stationId) {
        return bikeRepository.findByStationId(stationId);
    }




}
