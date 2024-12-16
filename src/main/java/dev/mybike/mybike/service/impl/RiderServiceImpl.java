package dev.mybike.mybike.service.impl;

import dev.mybike.mybike.model.Rider;
import dev.mybike.mybike.repository.RiderRepository;
import dev.mybike.mybike.service.RiderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Implementation of the RiderService interface that provides business logic
 * for managing rider-related functionalities in a bike-sharing system.

 * This service interacts with the RiderRepository to handle operations such as:
 * - Retrieving all riders.
 * - Fetching details of a specific rider by their identifier.
 * - Creating a new rider.
 * - Deleting an existing rider.

 * The service includes logic to handle cases where a rider is not found in the system
 * and ensures appropriate database operations for persistence.
 */
@Service
public class RiderServiceImpl implements RiderService {

    @Autowired
    private RiderRepository riderRepository;

    @Override
    public List<Rider> getAllRiders() {
        return riderRepository.findAll();
    }

    @Override
    public Rider getRiderById(String id) {
        return riderRepository.findById(id).orElseThrow(() -> new RuntimeException("Rider not found"));
    }

    @Override
    public Rider createRider(Rider rider) {
        return riderRepository.save(rider);
    }


    @Override
    public void deleteRider(String id) {
        riderRepository.deleteById(id);
    }
}