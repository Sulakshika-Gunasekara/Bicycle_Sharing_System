package dev.mybike.mybike.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import dev.mybike.mybike.model.Rider;

public interface RiderRepository extends MongoRepository<Rider, String> {
    Optional<Rider> findByUsername(String username);
}
