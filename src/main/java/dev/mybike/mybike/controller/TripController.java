package dev.mybike.mybike.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.mybike.mybike.model.Rider;
import dev.mybike.mybike.model.Trip;
import dev.mybike.mybike.service.RiderService;
import dev.mybike.mybike.service.TripService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("api/trip")
@ControllerAdvice
public class TripController {

    @Autowired
    private TripService tripService;

    @Autowired
    private RiderService riderService;

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

    @PostMapping("/calculatePayment/{tripId}/{timeDuration}")
    public ResponseEntity<String> calculatePayment(@PathVariable String tripId, @PathVariable String timeDuration) {
        try {
            double paymentAmount = tripService.calculatePayment(tripId, timeDuration);
            return ResponseEntity.ok("Payment amount: " + paymentAmount);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error calculating payment: " + e.getMessage());
        }
    }

    @PutMapping("/updateWalletBalance/{riderId}/{amount}")
    public ResponseEntity<String> updateWalletBalance(@PathVariable String riderId, @PathVariable double amount) {
        try {
            Rider rider = new Rider();
            rider.setId(riderId);
            double newBalance = tripService.updateWalletBalance(rider, amount);
            return ResponseEntity.ok("Wallet balance updated successfully. New balance: " + newBalance);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating wallet balance: " + e.getMessage());
        }
    }

    @PostMapping("/startTrip")
    public ResponseEntity<Trip> startTrip(@RequestBody Trip trip) {
        try {
            Rider rider = riderService.getRiderById(trip.getRiderId());
            Trip newTrip = tripService.startTrip(rider, trip.getBikeId());
            return ResponseEntity.ok(newTrip);
        } catch (Exception e) {
            return ResponseEntity.ok(trip);

        }
    }

}
