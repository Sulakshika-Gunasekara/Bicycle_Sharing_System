package dev.mybike.mybike.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import dev.mybike.mybike.model.Bike;
import java.util.List;


public interface BikeRepository extends MongoRepository<Bike,String>{

    List<Bike> findByStationId(String stationId);
    List<Bike> findByIsAvailable(boolean isAvailable);

}
