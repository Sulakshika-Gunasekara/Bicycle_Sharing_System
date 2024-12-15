package dev.mybike.mybike.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.mybike.mybike.model.Bike;
import dev.mybike.mybike.service.BikeService;

@RestController
@RequestMapping("api/bike")
@ControllerAdvice
public class BikeController {

    @Autowired
    private BikeService bikeService;

    @PostMapping("/{bikeId}/report-issue")
    public ResponseEntity<Bike> reportBikeIssue(@PathVariable String bikeId, @RequestBody ReportBikeIssueRequest request) {
        Bike bike = bikeService.reportBikeIssue(bikeId, request.getIssueType(), request.getDescription());
        return ResponseEntity.ok(bike);
    }

    @Data
    static class ReportBikeIssueRequest {
        private String issueType;
        private String description;
    }

    @PostMapping("/reserve")
    public ResponseEntity<Bike> reserveBike(@RequestBody ReserveBikeRequest request) {
        Bike bike = bikeService.reserveBike(request.getBikeId(), request.getStationId(), request.getDuration());
        return ResponseEntity.ok(bike);
    }

    @Data
    static class ReserveBikeRequest {
        private String bikeId;
        private String stationId;
        private int duration;     
    }

     @GetMapping("/{bikeId}/track")
    public ResponseEntity<Bike> trackBike(@PathVariable String bikeId) {
        Bike bike = bikeService.trackBike(bikeId);
        return ResponseEntity.ok(bike);
    }

    @GetMapping("/available/{stationId}")
    public ResponseEntity<List<Bike>> getAvailableBikes(@PathVariable String stationId) {
        List<Bike> Bikes = bikeService.getAvailableBikes(stationId);
        return ResponseEntity.ok(Bikes);
    }
    
}
