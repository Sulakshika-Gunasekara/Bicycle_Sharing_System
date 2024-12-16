package dev.mybike.mybike.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import dev.mybike.mybike.model.Bike;
import java.util.List;


/**
 * Repository interface for managing Bike entities using MongoDB.
 * It provides methods for querying bike data based on specific conditions
 * such as station ID and availability status.
 *
 * This interface extends the MongoRepository interface, leveraging Spring Data's
 * repository abstraction for CRUD operations on Bike entities.
 *
 * Methods:
 * - findByStationId: Retrieves a list of bikes assigned to a specific docking station.
 * - findByIsAvailable: Retrieves a list of bikes based on their availability status.
 */
public interface BikeRepository extends MongoRepository<Bike,String>{

    List<Bike> findByStationId(String stationId);
    List<Bike> findByIsAvailable(boolean isAvailable);

}
