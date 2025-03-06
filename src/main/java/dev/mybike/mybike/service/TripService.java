package dev.mybike.mybike.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import dev.mybike.mybike.model.Bike;
import dev.mybike.mybike.model.Rider;
import dev.mybike.mybike.model.Trip;
import dev.mybike.mybike.repository.BikeRepository;
import dev.mybike.mybike.repository.DockingStationRepository;
import dev.mybike.mybike.repository.RiderRepository;
import dev.mybike.mybike.repository.TripRepository;

@Service
public class TripService {
    
    private final TripRepository tripRepository;
    private final RiderRepository riderRepository;
    private final BikeRepository bikeRepository;
    private final DockingStationRepository dockingStationRepository;

    public TripService(TripRepository tripRepository, RiderRepository riderRepository, 
                       BikeRepository bikeRepository, DockingStationRepository dockingStationRepository) {
        this.tripRepository = tripRepository;
        this.riderRepository = riderRepository;
        this.bikeRepository = bikeRepository;
        this.dockingStationRepository = dockingStationRepository;
    }

    public Trip startTrip(String riderId, String bikeId) {
        // Check if rider exists
        Optional<Rider> riderOpt = riderRepository.findById(riderId);
        if (riderOpt.isEmpty()) {
            throw new RuntimeException("Rider not found");
        }

        // Check if bike exists and is available
        Optional<Bike> bikeOpt = bikeRepository.findById(bikeId);
        if (bikeOpt.isEmpty() || !bikeOpt.get().isAvailable()) {
            throw new RuntimeException("Bike not available");
        }

        Bike bike = bikeOpt.get();
        Trip trip = new Trip();
        trip.setRiderId(riderId);
        trip.setBikeId(bikeId);
        trip.setStartStationId(bike.getStationId());
        trip.setStartTime(LocalDateTime.now());
        trip.setCompleted(false);

        // Update bike availability
        bike.setAvailable(false);
        bikeRepository.save(bike);

        return tripRepository.save(trip);
    }

    public Trip endTrip(String riderId, String bikeId, String dockingStationId) {
        // Find active trip for the rider
        Optional<Trip> tripOpt = tripRepository.findByRiderIdAndIsCompleted(riderId, false)
                                               .stream().findFirst();
        if (tripOpt.isEmpty()) {
            throw new RuntimeException("No active trip found");
        }

        Trip trip = tripOpt.get();
        trip.setEndTime(LocalDateTime.now());
        trip.setEndStationId(dockingStationId);
        trip.setCompleted(true);

        // Calculate trip duration
        Duration duration = Duration.between(trip.getStartTime(), trip.getEndTime());
        long minutesUsed = duration.toMinutes();

        // Assume a rate of $0.10 per minute
        double cost = minutesUsed * 0.10;
        trip.setTripCost(cost);

        // Deduct from rider's wallet
        Optional<Rider> riderOpt = riderRepository.findById(riderId);
        if (riderOpt.isPresent()) {
            Rider rider = riderOpt.get();
            if (rider.getWalletBalance() < cost) {
                throw new RuntimeException("Insufficient balance");
            }
            rider.setWalletBalance(rider.getWalletBalance() - cost);
            riderRepository.save(rider);
        }

        // Update bike availability
        Optional<Bike> bikeOpt = bikeRepository.findById(bikeId);
        if (bikeOpt.isPresent()) {
            Bike bike = bikeOpt.get();
            bike.setAvailable(true);
            bike.setStationId(dockingStationId);
            bikeRepository.save(bike);
        }

        return tripRepository.save(trip);
    }
}