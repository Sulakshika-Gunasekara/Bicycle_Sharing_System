package dev.mybike.mybike.repository;

import dev.mybike.mybike.model.Rider;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RiderRepository extends MongoRepository<Rider, String> {
}
