package dev.mybike.mybike.service;

import dev.mybike.mybike.model.Rider;

import java.util.List;

public interface RiderService {
    List<Rider> getAllRiders();

    Rider getRiderById(String id);

    Rider createRider(Rider rider);

    void deleteRider(String id);

}