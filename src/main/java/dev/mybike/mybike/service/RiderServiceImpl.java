package dev.mybike.mybike.service;

import dev.mybike.mybike.model.Rider;
import dev.mybike.mybike.repository.RiderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RiderServiceImpl implements RiderService {

    @Autowired
    private RiderRepository riderRepository;

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
        return riderRepository.save(rider);
    }


    @Override
    public void deleteRider(String id) {
        riderRepository.deleteById(id);
    }
}