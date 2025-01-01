package dev.mybike.mybike.service;

import java.util.List;

import dev.mybike.mybike.model.Rider;

public interface RiderService {
    List<Rider> getAllRiders();

    Rider getRiderById(String id);

    Rider createRider(Rider rider);

    void deleteRider(String id);

    Rider loadRiderByUsername(String username);
}
