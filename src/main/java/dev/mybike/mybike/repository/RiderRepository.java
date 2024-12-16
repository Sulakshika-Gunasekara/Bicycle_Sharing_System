package dev.mybike.mybike.repository;

import dev.mybike.mybike.model.Rider;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Repository interface for managing Rider entities using MongoDB.
 *
 * This interface inherits from the MongoRepository interface, which provides
 * built-in support for standard CRUD operations, custom queries, and data access
 * abstraction for Rider entities.
 *
 * Responsibilities:
 * - Provides methods for standard database operations such as saving, updating,
 *   deleting, and retrieving Rider instances.
 * - Serves as a data access layer for Rider-related logic in the bike-sharing
 *   application.
 *
 * Extends:
 * - MongoRepository<Rider, String>
 *   - Rider: The entity class being managed by this repository.
 *   - String: The type of the unique identifier for the Rider entity.
 */
public interface RiderRepository extends MongoRepository<Rider, String> {
}
