package dev.mybike.mybike.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import dev.mybike.mybike.model.bike;
import java.util.List;


public interface bikeRepository extends MongoRepository<bike,String>{

    List<bike> findByStationId(String stationId);
    List<bike> findByIsAvailable(boolean isAvailable);

}
