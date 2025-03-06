package dev.mybike.mybike.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.mybike.mybike.model.Trip;
import dev.mybike.mybike.service.TripService;

@RestController
@RequestMapping("/api/trips")
public class TripController {

    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @PreAuthorize("hasRole('RIDER')")   
    @PostMapping("/start")
    public ResponseEntity<Trip> startTrip(@RequestBody Map<String, String> requestBody) {
        String riderId = requestBody.get("riderId");
        String bikeId = requestBody.get("bikeId");

        if (riderId == null || bikeId == null) {
            return ResponseEntity.badRequest().body(null);
        }

        return ResponseEntity.ok(tripService.startTrip(riderId, bikeId));
    }

    @PreAuthorize("hasRole('RIDER')")
    @PostMapping("/end")
    public ResponseEntity<Trip> endTrip(@RequestBody Map<String, String> requestBody) {
        String riderId = requestBody.get("riderId");
        String bikeId = requestBody.get("bikeId");
        String dockingStationId = requestBody.get("dockingStationId");

        if (riderId == null || bikeId == null || dockingStationId == null) {
            return ResponseEntity.badRequest().body(null);
        }

        return ResponseEntity.ok(tripService.endTrip(riderId, bikeId, dockingStationId));
    }
}