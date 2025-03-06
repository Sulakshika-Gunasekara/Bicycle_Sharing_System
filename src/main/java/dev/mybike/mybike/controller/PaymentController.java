package dev.mybike.mybike.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.mybike.mybike.service.StripeService;
import lombok.Data;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final StripeService stripeService;

    public PaymentController(StripeService stripeService) {
        this.stripeService = stripeService;
    }

    @PostMapping("/create-session")
    public ResponseEntity<?> createCheckoutSession(@RequestBody PaymentRequest paymentRequest) {
        try {
            if (paymentRequest.getAmount() == null || paymentRequest.getAmount() <= 0) {
                return ResponseEntity.badRequest().body("Invalid amount provided");
            }
            Map<String, String> response = stripeService.createCheckoutSession(
                    paymentRequest.getAmount(), paymentRequest.getRiderId());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating payment session: " + e.getMessage());
        }
    }
}

@Data
class PaymentRequest {
    private Double amount;
    private String riderId;
}