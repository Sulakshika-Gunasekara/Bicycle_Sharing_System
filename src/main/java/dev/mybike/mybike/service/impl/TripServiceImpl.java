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
    public double calculatePayment(Trip trip, Rider rider) {
        long durationInMillis = trip.getEndTime().getTime() - trip.getStartTime().getTime();
        long durationInMinutes = durationInMillis / (1000 * 60);
        double paymentAmount = durationInMinutes * RATE_PER_MINUTE;

        double currentBalance = rider.getWalletBalance();
        if (currentBalance >= paymentAmount) {
            rider.setWalletBalance(currentBalance - paymentAmount);
            // Additional logic to update the rider's wallet in the database
            System.out.println("Payment successful. New balance: " + rider.getWalletBalance());
        } else {
            System.out.println("Insufficient balance. Payment failed.");
        }

        return paymentAmount;
    }

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

}
