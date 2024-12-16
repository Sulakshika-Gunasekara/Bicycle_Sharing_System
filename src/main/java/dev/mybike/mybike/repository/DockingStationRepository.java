package dev.mybike.mybike.repository;

import dev.mybike.mybike.model.DockingStation;

/**
 * Repository interface for managing DockingStation entities using MongoDB.
 * It provides built-in CRUD operations by extending the MongoRepository interface.
 *
 * This interface supports operations for the DockingStation entity class, such as
 * finding docking stations by their unique ID or performing full entity CRUD.
 *
 * The primary purpose of this repository is to facilitate data access for docking station
 * management in the system, including retrieval, saving, updating, and deleting records
 * from the MongoDB database.
 *
 * Extends:
 * - MongoRepository<DockingStation, String>:
 *   - DockingStation: The entity class managed by this repository.
 *   - String: The type of the unique identifier for the DockingStation entity.
 */
public interface DockingStationRepository extends org.springframework.data.mongodb.repository.MongoRepository<DockingStation, String> {

}
