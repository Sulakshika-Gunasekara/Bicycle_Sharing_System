package dev.mybike.mybike.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import dev.mybike.mybike.model.Trip;

public interface TripRepository extends MongoRepository<Trip, String> {

}
