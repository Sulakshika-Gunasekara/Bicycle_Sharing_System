package dev.mybike.mybike.service;

import java.util.List;

import dev.mybike.mybike.model.Rider;

/**
 * Service interface for managing rider-related functionalities in a bike-sharing system.
 *
 * This interface defines the core operations related to riders, including:
 * - Retrieving a list of all riders.
 * - Fetching details of a specific rider by their unique identifier.
 * - Creating a new rider entity.
 * - Deleting a rider from the system.
 */
public interface RiderService {
    List<Rider> getAllRiders();

    Rider getRiderById(String id);

    Rider createRider(Rider rider);

    void deleteRider(String id);

    Rider loadRiderByRidername(String Ridername);

}
