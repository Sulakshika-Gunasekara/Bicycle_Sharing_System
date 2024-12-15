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

import dev.mybike.mybike.model.bike;
import dev.mybike.mybike.service.bikeService;

@RestController
@RequestMapping("api/bike")
@ControllerAdvice
public class bikeController {

   
    @Autowired
    private bikeService bikeService;

   @GetMapping
   public ResponseEntity<List<bike>> getAllBikes() {
      List<bike> bikes = bikeService.getAllBikes();
      return ResponseEntity.ok(bikes);
   }

   
    

   @GetMapping("/track/{bikeId}")
    public ResponseEntity<bike> trackBike(@PathVariable String bikeId) {
        bike bike = bikeService.trackBike(bikeId);
        return ResponseEntity.ok(bike);
    }

    @PostMapping("/reportIssue/{bikeId}/{issue}")
    public ResponseEntity<bike> reportIssueBike(@PathVariable String bikeId, @PathVariable String issue) {
        bike bike = bikeService.reportIssueBike(bikeId, issue);
        return ResponseEntity.ok(bike);
    }

    @PostMapping("reserve/{bikeId}")
    public ResponseEntity<bike> reserveBike(@PathVariable String bikeId){
       // bike bike = bikeService.reserveBike(bikeId);
        //return ResponseEntity.ok(bike);
         try {
        // Call the service method to reserve the bike
        bike bike = bikeService.reserveBike(bikeId);
        return ResponseEntity.ok(bike); // Return 200 OK with the bike details
    } catch (IllegalArgumentException ex) {
        // Handle exceptions and return 400 Bad Request with the error message
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
    }

    @GetMapping("/isAvailable/{isAvailable}")
    public ResponseEntity<List<bike>> getBikeByIsAvailable(@PathVariable Boolean isAvailable) {
        List<bike> bikes = bikeService.getBikeByIsAvailable(isAvailable);
        return ResponseEntity.ok(bikes);
    }

    @PutMapping("/updateBikeStation/{bikeId}/{oldStationId}/{newStationId}")
    public ResponseEntity<bike> updateBikeStation(@PathVariable String bikeId, @PathVariable String oldStationId, @PathVariable String newStationId) {
        bike bike = bikeService.updateBikeStation(bikeId, oldStationId, newStationId);
        return ResponseEntity.ok(bike);
    }
}
