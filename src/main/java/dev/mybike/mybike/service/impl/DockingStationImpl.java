package dev.mybike.mybike.service.impl;

import dev.mybike.mybike.model.DockingStation;
import dev.mybike.mybike.repository.DockingStationRepository;
import dev.mybike.mybike.service.DockingStationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

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
