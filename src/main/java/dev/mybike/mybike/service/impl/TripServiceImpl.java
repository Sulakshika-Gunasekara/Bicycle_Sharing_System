package dev.mybike.mybike.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.mybike.mybike.model.Rider;
import dev.mybike.mybike.model.Trip;
import dev.mybike.mybike.repository.TripRepository;
import dev.mybike.mybike.service.TripService;

@Service
public class TripServiceImpl implements TripService {

    @Autowired
    private TripRepository tripRepository;

    @Override
    public Trip getTripById(String id) {
        return tripRepository.findById(id).orElseThrow(() -> new RuntimeException("Rider not found"));
    }

    private static final double RATE_PER_MINUTE = 0.5; // Example rate

    @Override
    public double addFunds(Rider rider, double amount) {
        double currentBalance = rider.getWalletBalance();
        rider.setWalletBalance(currentBalance + amount);
        // Additional logic to update the rider's wallet in the database
        System.out.println("Funds added successfully. New balance: " + rider.getWalletBalance());
        return rider.getWalletBalance();
    }

    @Override
    public double deductFunds(Rider rider, double amount) {
        double currentBalance = rider.getWalletBalance();
        if (currentBalance >= amount) {
            rider.setWalletBalance(currentBalance - amount);
            // Additional logic to update the rider's wallet in the database
            System.out.println("Funds deducted successfully. New balance: " + rider.getWalletBalance());
        } else {
            System.out.println("Insufficient balance. Deduction failed.");
        }
        return rider.getWalletBalance();
    }

    @Override
    public double getBalance(Rider rider) {
        return rider.getWalletBalance();
    }

    @Override
    public double calculatePayment(String tripID, String timeDuration) {
        // Convert time duration string to minutes
        String[] timeParts = timeDuration.split(":");
        int hours = Integer.parseInt(timeParts[0]);
        int minutes = Integer.parseInt(timeParts[1]);
        int durationInMinutes = hours * 60 + minutes;

        // Calculate initial payment amount
        double paymentAmount = durationInMinutes * RATE_PER_MINUTE;

        // Apply deductions based on duration
        if (durationInMinutes < 30) {
            paymentAmount -= 1; // Deduct $1
        } else if (durationInMinutes >= 30 && durationInMinutes <= 60) {
            paymentAmount -= 3; // Deduct $3
        } else if (durationInMinutes > 60) {
            int extraHours = (durationInMinutes - 60) / 60; // Number of extra full hours
            paymentAmount -= 3 + (extraHours * 5); // Deduct $3 for first hour, $5 per extra hour

        }

        System.out.println("Payment amount for trip " + tripID + ": " + paymentAmount);
        return paymentAmount;
    }

    @Override
    public double updateWalletBalance(Rider rider, double amount) {
        double currentBalance = rider.getWalletBalance();
        rider.setWalletBalance(currentBalance - amount);
        // Additional logic to update the rider's wallet in the database
        System.out.println("Updated wallet balance: " + rider.getWalletBalance());
        return rider.getWalletBalance();
    }

    @Override
    public Trip startTrip(Rider rider, String bikeId) {
        Trip trip = new Trip();
        trip.setRiderId(rider.getId());
        trip.setBikeId(bikeId);

        // Additional logic to save the trip in the database
        System.out.println("Trip started successfully.");
        return trip;
    }

}
