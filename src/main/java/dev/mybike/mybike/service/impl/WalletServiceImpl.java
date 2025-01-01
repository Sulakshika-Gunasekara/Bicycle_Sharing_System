package dev.mybike.mybike.service.impl;

import dev.mybike.mybike.model.Trip;
import dev.mybike.mybike.model.Bike;
import dev.mybike.mybike.model.Rider;
import dev.mybike.mybike.service.WalletService;
import org.springframework.stereotype.Service;

@Service
public class WalletServiceImpl implements WalletService {

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

}