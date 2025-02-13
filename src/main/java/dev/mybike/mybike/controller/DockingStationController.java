package dev.mybike.mybike.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.mybike.mybike.model.DockingStation;
import dev.mybike.mybike.service.DockingStationService;

/**
 * A REST controller for managing docking station-related operations.
 * This controller provides endpoints for retrieving docking station data,
 * checking dynamic pricing, and managing the activation state of docking stations.
 */

@RestController
@RequestMapping("api/docking-station")
public class DockingStationController {

    @Autowired
    private DockingStationService dockingStationService;

    @PreAuthorize("hasRole('ADMIN','RIDER')")
    @GetMapping("/any/all")
     public ResponseEntity<List<DockingStation>> getAllDockingStations() {
        List<DockingStation> dockingStations = dockingStationService.getAllDockingStations();
        return ResponseEntity.ok(dockingStations);
    }

    @PreAuthorize("hasRole('ADMIN','RIDER')")
    @GetMapping("/any/{stationId}")
    public ResponseEntity<DockingStation> getDockingStationById(@PathVariable String stationId) {
        DockingStation dockingStation = dockingStationService.getDockingStationById(stationId);
        return ResponseEntity.ok(dockingStation);
    }

    @PreAuthorize("hasRole('RIDER')")
    @GetMapping("/rider/{stationId}/pricing")
    public ResponseEntity<Double> getDynamicPricing(@PathVariable String stationId) {
        double pricing = dockingStationService.calculateDynamicPricing(stationId);
        return ResponseEntity.ok(pricing);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/{stationId}/deactivate")
    public ResponseEntity<DockingStation> deactivateDockingStation(@PathVariable String stationId) {
        DockingStation updatedDockingStation = dockingStationService.deactivateDockingStation(stationId);
        return ResponseEntity.ok(updatedDockingStation);
    }
}
