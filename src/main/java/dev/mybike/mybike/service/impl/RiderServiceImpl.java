package dev.mybike.mybike.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dev.mybike.mybike.model.Rider;
import dev.mybike.mybike.repository.RiderRepository;
import dev.mybike.mybike.service.RiderService;

@Service
public class RiderServiceImpl implements RiderService {

    private final RiderRepository riderRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RiderServiceImpl(RiderRepository riderRepository, PasswordEncoder passwordEncoder) {
        this.riderRepository = riderRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<Rider> getAllRiders() {
        return riderRepository.findAll();
    }

    @Override
    public Rider getRiderById(String id) {
        return riderRepository.findById(id).orElseThrow(() -> new RuntimeException("Rider not found"));
    }

    @Override
    public Rider createRider(Rider rider) {
        // Encode the password before saving the rider
        rider.setPassword(passwordEncoder.encode(rider.getPassword()));
        return riderRepository.save(rider);
    }

    @Override
    public void deleteRider(String id) {
        riderRepository.deleteById(id);
    }

    @Override
    public Rider loadRiderByUsername(String username) {
        return riderRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Rider not found"));
    }
}