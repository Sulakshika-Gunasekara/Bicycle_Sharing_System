package dev.mybike.mybike.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

@Service
public class StripeService {

    private final String stripeSecretKey;
    private final String stripePublicKey;

    public StripeService(@Value("${stripe.secretKey}") String stripeSecretKey,
                         @Value("${stripe.publicKey}") String stripePublicKey) {
        this.stripeSecretKey = stripeSecretKey;
        this.stripePublicKey = stripePublicKey;
        
        
        Stripe.apiKey = stripeSecretKey;
    }

    public Map<String, String> createCheckoutSession(double amount, String riderId) throws StripeException {
        Stripe.apiKey = stripeSecretKey; 

        long amountInCents = (long) (amount * 100); 

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:5173/profile")
                .setCancelUrl("http://localhost:5173/payment-failed")
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("usd")
                                .setUnitAmount(amountInCents)
                                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                        .setName("Wallet Top-Up")
                                        .build())
                                .build())
                        .build())
                .putMetadata("riderId", riderId)
                .build();

        Session session = Session.create(params);

        Map<String, String> response = new HashMap<>();
        response.put("checkoutUrl", session.getUrl());
        return response;
    }

public void updateWalletBalance(String riderId, Double amount) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateWalletBalance'");
}
}