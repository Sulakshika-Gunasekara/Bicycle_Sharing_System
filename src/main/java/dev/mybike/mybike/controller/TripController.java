package dev.mybike.mybike.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.mybike.mybike.model.Rider;
import dev.mybike.mybike.model.Trip;
import dev.mybike.mybike.service.RiderService;
import dev.mybike.mybike.service.TripService;

@RestController
@RequestMapping("api/trip")
@ControllerAdvice
public class TripController {

    @Autowired
    private TripService tripService;

    @Autowired
    private RiderService riderService;

    // @PostMapping("/calculatePayment/{tripId}")
    // public ResponseEntity<String> calculatePayment(@PathVariable String tripId) {
    // try {
    // Trip trip = tripService.getTripById(tripId);

    // if (trip == null) {
    // return ResponseEntity.badRequest().body("Trip not found.");
    // }

    // // Retrieve the rider associated with the trip
    // Rider rider = trip.getRider();
    // if (rider == null) {
    // return ResponseEntity.badRequest().body("Rider not found.");
    // }

    // // Calculate payment and deduct from rider's wallet
    // double paymentAmount = walletService.calculatePayment(trip, rider);
    // return ResponseEntity.ok("Payment successful. New balance: " +
    // rider.getWalletBalance()
    // + ". Payment amount: " + paymentAmount);

    // } catch (Exception e) {
    // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    // .body("Error calculating payment" + e.getMessage());

    // }
    // }

    @PostMapping("/calculatePayment/{tripId}")
    public ResponseEntity<String> calculatePayment(@PathVariable String tripId, @PathVariable String riderId) {
        try {
            Trip trip = tripService.getTripById(tripId);

            if (trip == null) {
                return ResponseEntity.badRequest().body("Trip not found.");
            }

            // Retrieve the rider associated with the trip
            Rider rider = riderService.getRiderById(riderId);
            if (rider == null) {
                return ResponseEntity.badRequest().body("Rider not found.");
            }

            // Calculate payment and deduct from rider's wallet
            double paymentAmount = tripService.calculatePayment(trip, rider);
            return ResponseEntity.ok("Payment successful. New balance: " + rider.getWalletBalance()
                    + ". Payment amount: " + paymentAmount);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error calculating payment" + e.getMessage());

        }
    }

    @PostMapping("/addFunds/{riderId}/{amount}")
    public ResponseEntity<String> addFunds(@PathVariable String riderId, @PathVariable double amount) {
        try {
            Rider rider = new Rider();
            rider.setId(riderId);
            double newBalance = tripService.addFunds(rider, amount);
            return ResponseEntity.ok("Funds added successfully. New balance: " + newBalance);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error adding funds: " + e.getMessage());
        }
    }

    @PostMapping("/deductFunds/{riderId}/{amount}")
    public ResponseEntity<String> deductFunds(@PathVariable String riderId, @PathVariable double amount) {
        try {
            Rider rider = new Rider();
            rider.setId(riderId);
            double newBalance = tripService.deductFunds(rider, amount);
            return ResponseEntity.ok("Funds deducted successfully. New balance: " + newBalance);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deducting funds: " + e.getMessage());
        }
    }

    @GetMapping("/getBalance/{riderId}")
    public ResponseEntity<String> getBalance(@PathVariable String riderId) {
        try {
            Rider rider = new Rider();
            rider.setId(riderId);
            double balance = tripService.getBalance(rider);
            return ResponseEntity.ok("Current balance: " + balance);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error getting balance: " + e.getMessage());
        }
    }

}
