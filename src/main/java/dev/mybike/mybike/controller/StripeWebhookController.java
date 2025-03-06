package dev.mybike.mybike.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;

import dev.mybike.mybike.model.Rider;
import dev.mybike.mybike.repository.RiderRepository;

@RestController
@RequestMapping("/api/stripe-webhook")
public class StripeWebhookController {

    @Value("${stripe.secretKey}")
    private String stripeSecretKey;

    @Value("${stripe.webhookSecret}")
    private String stripeWebhookSecret;

    private final RiderRepository riderRepository;

    public StripeWebhookController(RiderRepository riderRepository) {
        this.riderRepository = riderRepository;
    }

    @PostMapping
    public ResponseEntity<?> handleStripeWebhook(@RequestBody String payload,
                                                 @RequestHeader("Stripe-Signature") String sigHeader) {
        Stripe.apiKey = stripeSecretKey;

        try {
            Event event = Webhook.constructEvent(payload, sigHeader, stripeWebhookSecret);

            if ("checkout.session.completed".equals(event.getType())) {
                Session session = (Session) event.getDataObjectDeserializer().getObject().orElse(null);
                if (session != null) {
                    String riderId = session.getMetadata().get("riderId");
                    double amountPaid = session.getAmountTotal() / 100.0;

                    // Update Rider's Wallet Balance
                    Optional<Rider> riderOpt = riderRepository.findById(riderId);
                    if (riderOpt.isPresent()) {
                        Rider rider = riderOpt.get();
                        rider.setWalletBalance(rider.getWalletBalance() + amountPaid);
                        riderRepository.save(rider);
                        return ResponseEntity.ok("Wallet updated successfully");
                    }
                }
            }
            return ResponseEntity.ok("Webhook received");
        } catch (SignatureVerificationException e) {
            return ResponseEntity.badRequest().body("Webhook verification failed");
        }
    }
}
