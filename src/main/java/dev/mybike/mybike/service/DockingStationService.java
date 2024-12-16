package dev.mybike.mybike.service;

import dev.mybike.mybike.model.DockingStation;
import java.util.List;

/**
 * Service interface for managing docking stations in a bike-sharing system.
 *
 * This interface defines the core operations related to docking stations, including:
 * - Retrieving a list of all available docking stations.
 * - Fetching specific docking station details using an identifier.
 * - Deactivating a given docking station.
 * - Calculating dynamic pricing based on the current availability of bikes at a station.
 */
public interface DockingStationService {
    List<DockingStation> getAllDockingStations();
    DockingStation getDockingStationById(String stationId);
    DockingStation deactivateDockingStation(String stationId);
    double calculateDynamicPricing(String stationId);

    
} 
