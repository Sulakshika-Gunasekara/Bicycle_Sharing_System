package dev.mybike.mybike.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.mybike.mybike.model.Rider;
import dev.mybike.mybike.service.RiderService;

@RestController
@RequestMapping("/api/riders")
public class RiderController {

    @Autowired
    private RiderService riderService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping
    public List<Rider> getAllRiders() {
        return riderService.getAllRiders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rider> getRiderById(@PathVariable String id) {
        return ResponseEntity.ok(riderService.getRiderById(id));
    }

    @PostMapping
    public Rider createRider(@RequestBody Rider rider) {
        return riderService.createRider(rider);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRider(@PathVariable String id) {
        riderService.deleteRider(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/register")
    public Rider registerRider(@RequestBody Rider rider) {
        return riderService.createRider(rider);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginRider(@RequestBody Rider rider) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(rider.getUsername(), rider.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return ResponseEntity.ok("Login successful");
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
}