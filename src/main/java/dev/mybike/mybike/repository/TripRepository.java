package dev.mybike.mybike.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import dev.mybike.mybike.model.Trip;

public interface TripRepository extends MongoRepository<Trip, String> {
    Optional<Trip> findByUsername(String username);
}
