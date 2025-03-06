package dev.mybike.mybike.service;

import dev.mybike.mybike.model.Rider;
import dev.mybike.mybike.model.Trip;

public interface TripService {

    Trip getTripById(String tripId);

    double addFunds(Rider rider, double amount);

    double deductFunds(Rider rider, double amount);

    double getBalance(Rider rider);

    // get time duration of trip as data and calculate the payment amount based on
    // duration of trip
    // after a trip this will show the the amount to be paid
    double calculatePayment(String tripID, String timeDuration); // add time duration as a string parameter

    // update riders wallet balance
    // return updated wallet balance
    // get the rider and the amount of the last ended trip which return from
    // calculatePayment
    // update the wallet balance of the rider by reducing the amount of the last
    // trip

    double updateWalletBalance(Rider rider, double amount);

    // add a new trip to the database
    // return the trip
    Trip startTrip(Rider rider, String bikeId);

}