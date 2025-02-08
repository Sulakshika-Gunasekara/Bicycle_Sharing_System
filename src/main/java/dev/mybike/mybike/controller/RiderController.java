package dev.mybike.mybike.controller;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    private String ADMIN; // Should be "ADMIN"

    @Value("${role.rider}")
    private String RIDER; // Should be "RIDER"

    // Get protected data based on role
    @GetMapping("/protected-data")
    public ResponseEntity<String> getProtectedData(@RequestHeader("Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            String jwtToken = token.substring(7);

            try {
                if (jwtUtil.isTokenVaild(jwtToken)) {
                    String username = jwtUtil.extractUsername(jwtToken);
                    Set<String> roles = jwtUtil.extractRoles(jwtToken);

                    System.out.println("Extracted Roles: " + roles); // Debugging log

                    if (roles.contains(ADMIN)) {
                        return ResponseEntity.ok("Welcome " + username + ", you have ADMIN access.");
                    } else if (roles.contains(RIDER)) {
                        return ResponseEntity.ok("Welcome " + username + ", you have RIDER access.");
                    } else {
                        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied");
                    }
                }
            } catch (Exception ex) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid Token");
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
            return riderOpt.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
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

    // Get rider data without password
    @GetMapping("/rider-data")
    public ResponseEntity<?> getRiderData(@RequestHeader("Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            String jwtToken = token.substring(7);
            String username = jwtUtil.extractUsername(jwtToken);

            Optional<Rider> riderOpt = riderRepository.findByRidername(username);
            if (riderOpt.isPresent()) {
                Rider rider = riderOpt.get();
                rider.setPassword(null); // Remove password from the response
                return ResponseEntity.ok(rider);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rider not found");
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization Header missing or invalid");
    }

    // List all riders (ADMIN and RIDER access)
    @GetMapping("/riders")
    public ResponseEntity<?> listAllRiders(@RequestHeader("Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            String jwtToken = token.substring(7);

            if (jwtUtil.isTokenVaild(jwtToken)) {
                Set<String> roles = jwtUtil.extractRoles(jwtToken);
                System.out.println("Extracted Roles for /riders: " + roles); // Debugging log

                if (roles.contains(ADMIN) || roles.contains(RIDER)) {
                    return ResponseEntity.ok(riderRepository.findAll());
                } else {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied");
                }
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization Header missing or invalid");
    }

    // Delete authenticated rider's profile (RIDER access)
    @DeleteMapping("/profile")
    public ResponseEntity<?> deleteUserProfile(@RequestHeader("Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            String jwtToken = token.substring(7);
            String username = jwtUtil.extractUsername(jwtToken);

            Optional<Rider> riderOpt = riderRepository.findByRidername(username);
            if (riderOpt.isPresent()) {
                riderRepository.delete(riderOpt.get());
                return ResponseEntity.ok("Profile deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rider not found");
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization Header missing or invalid");
    }

    // Delete any rider's profile (ADMIN access)
    @DeleteMapping("/profile/{ridername}")
    public ResponseEntity<?> deleteRiderProfile(@RequestHeader("Authorization") String token, @PathVariable String ridername) {
        if (token != null && token.startsWith("Bearer ")) {
            String jwtToken = token.substring(7);

            if (jwtUtil.isTokenVaild(jwtToken)) {
                Set<String> roles = jwtUtil.extractRoles(jwtToken);

                if (roles.contains(ADMIN)) {
                    Optional<Rider> riderOpt = riderRepository.findByRidername(ridername);
                    if (riderOpt.isPresent()) {
                        riderRepository.delete(riderOpt.get());
                        return ResponseEntity.ok("Rider profile deleted successfully");
                    } else {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rider not found");
                    }
                } else {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied");
                }
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization Header missing or invalid");
    }
}