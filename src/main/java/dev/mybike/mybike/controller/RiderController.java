package dev.mybike.mybike.controller;

import dev.mybike.mybike.model.Rider;
import dev.mybike.mybike.service.RiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing rider-related operations.
 * This controller provides endpoints for accessing, creating,
 * and deleting rider information.
 */
@RestController
@RequestMapping("/api/riders")
public class RiderController {

    @Autowired
    private RiderService riderService;

    @GetMapping
    public List<Rider> getAllRiders() {
        return riderService.getAllRiders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rider> getRiderById(@PathVariable String id) {
        return ResponseEntity.ok(riderService.getRiderById(id));
    }

    @PostMapping
    public Rider createRider(@RequestBody Rider rider) {
        return riderService.createRider(rider);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRider(@PathVariable String id) {
        riderService.deleteRider(id);
        return ResponseEntity.noContent().build();
    }
}
