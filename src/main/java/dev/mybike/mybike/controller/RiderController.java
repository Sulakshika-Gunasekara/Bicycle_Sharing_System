package dev.mybike.mybike.controller;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.mybike.mybike.model.Rider;
import dev.mybike.mybike.repository.RiderRepository;
import dev.mybike.mybike.security.JwtUtil;

@RestController
@RequestMapping("/auth")
public class RiderController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RiderRepository riderRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${role.admin}")
    private String ADMIN;

    @Value("${role.user}")
    private String USER;

    // Endpoint to access user protected resources
    @GetMapping("/protected-data")
    public ResponseEntity<String> getProtectedData(@RequestHeader("Authorization") String token){
        if(token != null && token.startsWith("Bearer ")){
            String jwtToken = token.substring(7);

            try{
                if(jwtUtil.isTokenVaild(jwtToken)){
                    String username = jwtUtil.extractUsername(jwtToken);

                    Set<String> roles = jwtUtil.extractRoles(jwtToken);

                    if(roles.contains(ADMIN)){
                        return ResponseEntity.ok("Welcome "+username+" Here is the "+roles+" Specific data");
                    }else if(roles.contains(USER)){
                        return ResponseEntity.ok("Welcome "+username+" Here is the "+roles+" Specific data");
                    }
                    else {
                        return ResponseEntity.status(403).body("Access Denied");
                    }
                }
            }catch (Exception ex){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invaild Token");
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization Header missing or invalid");
    }



    // Get authenticated rider's profile
    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(@RequestHeader("Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            String jwtToken = token.substring(7);
            String username = jwtUtil.extractUsername(jwtToken);

            Optional<Rider> riderOpt = riderRepository.findByRidername(username);
            if (riderOpt.isPresent()) {
                return ResponseEntity.ok(riderOpt.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rider not found");
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization Header missing or invalid");
    }

    // Update authenticated rider's profile
    @PutMapping("/profile")
    public ResponseEntity<?> updateUserProfile(@RequestHeader("Authorization") String token,
                                               @RequestBody Rider updatedRider) {
        if (token != null && token.startsWith("Bearer ")) {
            String jwtToken = token.substring(7);
            String username = jwtUtil.extractUsername(jwtToken);

            Optional<Rider> riderOpt = riderRepository.findByRidername(username);
            if (riderOpt.isPresent()) {
                Rider rider = riderOpt.get();

                rider.setFirstName(updatedRider.getFirstName());
                rider.setLastName(updatedRider.getLastName());
                rider.setEmail(updatedRider.getEmail());
                rider.setMobileNumber(updatedRider.getMobileNumber());
                rider.setLocation(updatedRider.getLocation());

                if (updatedRider.getPassword() != null && !updatedRider.getPassword().isEmpty()) {
                    rider.setPassword(passwordEncoder.encode(updatedRider.getPassword()));
                }

                riderRepository.save(rider);
                return ResponseEntity.ok("Profile updated successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rider not found");
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization Header missing or invalid");
    }
}