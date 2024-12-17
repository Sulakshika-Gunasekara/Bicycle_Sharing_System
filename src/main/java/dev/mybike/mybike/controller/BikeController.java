package dev.mybike.mybike.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import lombok.Data;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.mybike.mybike.model.Bike;
import dev.mybike.mybike.service.BikeService;

/**
 * REST controller for managing bike-related operations.
 * This controller provides endpoints for reporting issues,
 * reserving bikes, tracking bike location, and retrieving available bikes.
 */
@RestController
@RequestMapping("api/bike")
@ControllerAdvice
public class BikeController {

   @Autowired
    private BikeService bikeService;

   @GetMapping
   public ResponseEntity<List<Bike>> getAllBikes() {
      List<Bike> bikes = bikeService.getAllBikes();
      return ResponseEntity.ok(bikes);
   }

   @GetMapping("/{bikeId}")
    public ResponseEntity<Bike> getBikeById(@PathVariable String bikeId) {
         Bike bike = bikeService.getBikeById(bikeId);
        return ResponseEntity.ok(bike);
    }
    

   @GetMapping("/track/{bikeId}")
    public ResponseEntity<Bike> trackBike(@PathVariable String bikeId) {
        Bike bike = bikeService.trackBike(bikeId);
        return ResponseEntity.ok(bike);
    }

    @PostMapping("/reportIssue/{bikeId}/{issue}")
    public ResponseEntity<Bike> reportIssueBike(@PathVariable String bikeId, @PathVariable String issue) {
        Bike bike = bikeService.reportIssueBike(bikeId, issue);
        return ResponseEntity.ok(bike);
    }

    @PostMapping("reserve/{bikeId}")
    public ResponseEntity<Bike> reserveBike(@PathVariable String bikeId){
       // bike bike = bikeService.reserveBike(bikeId);
        //return ResponseEntity.ok(bike);
         try {
        // Call the service method to reserve the bike
        Bike bike = bikeService.reserveBike(bikeId);
        return ResponseEntity.ok(bike); // Return 200 OK with the bike details
    } catch (IllegalArgumentException ex) {
        // Handle exceptions and return 400 Bad Request with the error message
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
    }

    @GetMapping("/isAvailable/{isAvailable}")
    public ResponseEntity<List<Bike>> getBikeByIsAvailable(@PathVariable Boolean isAvailable) {
        List<Bike> bikes = bikeService.getBikeByIsAvailable(isAvailable);
        return ResponseEntity.ok(bikes);
    }

    @PutMapping("/updateBikeStation/{bikeId}/{oldStationId}/{newStationId}")
    public ResponseEntity<Bike> updateBikeStation(@PathVariable String bikeId, @PathVariable String oldStationId, @PathVariable String newStationId) {
        Bike bike = bikeService.updateBikeStation(bikeId, oldStationId, newStationId);
        return ResponseEntity.ok(bike);
    }
    
}