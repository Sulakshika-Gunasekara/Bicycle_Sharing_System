package dev.mybike.mybike.service;

import dev.mybike.mybike.model.DockingStation;
import java.util.List;

public interface DockingStationService {
    List<DockingStation> getAllDockingStations();
    DockingStation getDockingStationById(String stationId);
    DockingStation deactivateDockingStation(String stationId);
    double calculateDynamicPricing(String stationId);

    
} 
