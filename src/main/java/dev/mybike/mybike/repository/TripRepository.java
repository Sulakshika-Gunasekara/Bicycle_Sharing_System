package dev.mybike.mybike.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import dev.mybike.mybike.model.Trip;

public interface TripRepository extends MongoRepository<Trip, String> {
    List<Trip> findByRiderIdAndIsCompleted(String riderId, boolean isCompleted);
}