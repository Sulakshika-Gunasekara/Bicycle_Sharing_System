package dev.mybike.mybike.service;

import dev.mybike.mybike.model.Rider;
import dev.mybike.mybike.model.Trip;

public interface TripService {

    Trip getTripById(String tripId);

    double calculatePayment(Trip trip, Rider rider);

    double addFunds(Rider rider, double amount);

    double deductFunds(Rider rider, double amount);

    double getBalance(Rider rider);

}
