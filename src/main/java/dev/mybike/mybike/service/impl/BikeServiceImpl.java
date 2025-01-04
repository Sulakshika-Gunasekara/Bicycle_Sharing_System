package dev.mybike.mybike.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.mybike.mybike.model.Bike;
import dev.mybike.mybike.model.DockingStation;
import dev.mybike.mybike.model.Trip;
import dev.mybike.mybike.repository.BikeRepository;
import dev.mybike.mybike.repository.DockingStationRepository;
import dev.mybike.mybike.repository.TripRepository;
import dev.mybike.mybike.service.BikeService;

/**
 * Implementation of the BikeService interface that provides business logic
 * for managing bike-related functionalities in a bike-sharing system.
 *
 * This service interacts with the BikeRepository to handle operations such as:
 * - Reporting Bike issues.
 * - Reserving bikes for users.
 * - Tracking a specific bike's details.
 * - Retrieving available bikes at a specific docking station.
 *
 * The service includes validation of Bike availability and handles cases
 * where the requested Bike or data is not found.
 */
@Service
public class BikeServiceImpl implements BikeService {

    @Autowired
    private BikeRepository bikeRepository;

    @Autowired
    private DockingStationRepository dockingStationRepository;

    @Autowired
    private TripRepository tripRepository;

    @Override
    public List<Bike> getAllBikes() {
        return bikeRepository.findAll();
    }

    @Override
    public Bike getBikeById(String bike) {
        return bikeRepository.findById(bike)
                .orElseThrow(() -> new IllegalArgumentException("Bike not found."));
    }

    @Override
    public Bike trackBike(String bikeId) {
        Bike bike = bikeRepository.findById(bikeId)
                .orElseThrow(() -> new IllegalArgumentException("Bike not found."));
        // Add tracking logic here
        return bike;
    }

    @Override
    public Bike reportIssueBike(String bikeId, String issue) {
        Bike bike = bikeRepository.findById(bikeId)
                .orElseThrow(() -> new IllegalArgumentException("Bike not found."));
        // Add issue reporting logic here
        bike.setCondition("Issue: " + issue);
        return bikeRepository.save(bike);
    }

    @Override
    public Bike reserveBike(String bikeId, String oldStationId) {
        Bike bike = bikeRepository.findById(bikeId)
                .orElseThrow(() -> new IllegalArgumentException("Bike not found."));
        if (!bike.isAvailable()) {
            throw new IllegalArgumentException("Bike is not available.");
        }
        bike.setAvailable(false);
        // get Date and time
        // set start time
        Trip trip = tripRepository.findById(bikeId)
                .orElseThrow(() -> new IllegalArgumentException("Trip not found."));
        Date currentTime = new Date(System.currentTimeMillis());
        trip.setStartTime(currentTime);
        tripRepository.save(trip);

        // update new staiton available bikes
        // update old station empty docks
        // update new station empty docks
        // update old station available bikes
        // update Bike station id
        DockingStation oldDockingStation = dockingStationRepository.findById(oldStationId)
                .orElseThrow(() -> new RuntimeException("Old Docking Station not found."));

        oldDockingStation.setEmptyDocks(oldDockingStation.getEmptyDocks() + 1);
        oldDockingStation.setAvailableBikes(oldDockingStation.getAvailableBikes() - 1);
        dockingStationRepository.save(oldDockingStation);

        return bikeRepository.save(bike);
    }

    @Override
    public Bike returnBike(String bikeId, String newStationId) {
        Bike bike = bikeRepository.findById(bikeId)
                .orElseThrow(() -> new IllegalArgumentException("Bike not found."));
        if (bike.isAvailable()) {
            throw new IllegalArgumentException("Bike is already available.");
        }
        bike.setAvailable(true);
        // get Date and time
        // set return time
        Trip trip = tripRepository.findById(bikeId)
                .orElseThrow(() -> new IllegalArgumentException("Trip not found."));
        trip.setEndTime(new Date(System.currentTimeMillis()));
        tripRepository.save(trip);

        // update new staiton available bikes
        // update old station empty docks
        // update new station empty docks
        // update old station available bikes
        // update Bike station id
        DockingStation newDockingStation = dockingStationRepository.findById(newStationId)
                .orElseThrow(() -> new RuntimeException("New Docking Station not found."));
        newDockingStation.setEmptyDocks(newDockingStation.getEmptyDocks() - 1);
        newDockingStation.setAvailableBikes(newDockingStation.getAvailableBikes() + 1);
        dockingStationRepository.save(newDockingStation);

        bike.setStationId(newStationId);

        return bikeRepository.save(bike);
    }

    @Override
    public List<Bike> getBikeByIsAvailable(Boolean isAvailable) {
        return bikeRepository.findByIsAvailable(isAvailable);
    }

    @Transactional
    @Override
    public Bike updateBikeStation(String bikeId, String oldStationId, String newStationId) {
        Bike bike = bikeRepository.findById(bikeId)
                .orElseThrow(() -> new RuntimeException("Bike not found."));

        // update new staiton available bikes
        // update old station empty docks
        // update new station empty docks
        // update old station available bikes
        // update Bike station id
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
}
