package dev.mybike.mybike.service;

import dev.mybike.mybike.model.Rider;
import dev.mybike.mybike.model.Trip;
import dev.mybike.mybike.model.Bike;

/**
 * Service interface for managing wallet operations in a bike-sharing system.
 *
 * This interface defines the core operations related to wallet management,
 * including:
 * - Adding funds to a user's wallet.
 * - Deducting funds from a user's wallet.
 * - Fetching the current balance of a user's wallet.
 * - Handling transactions between users.
 */
public interface WalletService {

    double calculatePayment(Trip trip, Rider rider);

}
