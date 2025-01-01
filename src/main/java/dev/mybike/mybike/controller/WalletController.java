package dev.mybike.mybike.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.mybike.mybike.model.Rider;
import dev.mybike.mybike.model.Trip;
import dev.mybike.mybike.service.WalletService;
import dev.mybike.mybike.service.TripService;

@RestController
@RequestMapping("api/wallet")
@ControllerAdvice
public class WalletController {

    @Autowired
    private WalletService walletService;

    @Autowired
    private TripService tripService;

    @PostMapping("/calculatePayment/{tripId}")
    public ResponseEntity<String> calculatePayment(@PathVariable String tripId) {
        try {
            Trip trip = tripService.getTripById(tripId);

            if (trip == null) {
                return ResponseEntity.badRequest().body("Trip not found.");
            }

            // Retrieve the rider associated with the trip
            Rider rider = trip.getRider();
            if (rider == null) {
                return ResponseEntity.badRequest().body("Rider not found.");
            }

            // Calculate payment and deduct from rider's wallet
            double paymentAmount = walletService.calculatePayment(trip, rider);
            return ResponseEntity.ok("Payment successful. New balance: " + rider.getWalletBalance()
                    + ". Payment amount: " + paymentAmount);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error calculating payment" + e.getMessage());

        }
    }
}
