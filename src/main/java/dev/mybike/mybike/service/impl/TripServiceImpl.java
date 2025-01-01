package dev.mybike.mybike.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.mybike.mybike.model.Trip;
import dev.mybike.mybike.repository.TripRepository;
import dev.mybike.mybike.service.TripService;

@Service
public class TripServiceImpl implements TripService {

    @Autowired
    private TripRepository tripRepository;

    @Override
    public Trip getTripById(String id) {
        return tripRepository.findById(id).orElseThrow(() -> new RuntimeException("Rider not found"));
    }

}
