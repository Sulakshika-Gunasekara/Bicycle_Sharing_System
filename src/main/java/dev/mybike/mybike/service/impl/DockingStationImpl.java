package dev.mybike.mybike.service.impl;

import dev.mybike.mybike.model.DockingStation;
import dev.mybike.mybike.repository.DockingStationRepository;
import dev.mybike.mybike.service.DockingStationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Implementation of the DockingStationService interface that provides the business logic
 * for managing docking stations in the bike-sharing system.
 *
 * This service interacts with DockingStationRepository to perform CRUD operations
 * and additional functionalities such as:
 * - Retrieving a list of all docking stations.
 * - Fetching details of a specific docking station by its identifier.
 * - Deactivating a docking station.
 * - Calculating dynamic pricing based on the availability of bikes at a station.
 *
 * This class includes logic to handle cases where a docking station is not found
 * or where specific operational conditions must be validated, such as bike availability.
 */
@Service
public class DockingStationImpl implements DockingStationService{

    @Autowired
    private DockingStationRepository dockingStationRepository;

    @Override
    public List<DockingStation> getAllDockingStations() {
        return dockingStationRepository.findAll();
    }

    @Override
    public DockingStation getDockingStationById(String stationId) {
        return dockingStationRepository.findById(stationId)
                .orElseThrow(() -> new IllegalArgumentException("Docking station not found."));
    }

    @Override
    public DockingStation deactivateDockingStation(String stationId) {
        DockingStation dockingStation = getDockingStationById(stationId);
        dockingStation.setActive(false);
        return dockingStationRepository.save(dockingStation);
    }

    @Override
    public double calculateDynamicPricing(String stationId) {
        DockingStation dockingStation = getDockingStationById(stationId);
        int totalCapacity = dockingStation.getAvailableBikes() + dockingStation.getEmptyDocks();
        double baseRate = 1.0; // Example base rate
        double surgeMultiplier = dockingStation.getAvailableBikes() < (0.2 * totalCapacity) ? 2.0 : 1.0;
        return baseRate * surgeMultiplier;
    }
}
