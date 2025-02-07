package dev.mybike.mybike.controller;


import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.mybike.mybike.dto.LoginRequest;
import dev.mybike.mybike.dto.RegisterRequest;
import dev.mybike.mybike.dto.VerifyRequest;
import dev.mybike.mybike.model.Rider;
import dev.mybike.mybike.model.Role;
import dev.mybike.mybike.repository.RiderRepository;
import dev.mybike.mybike.repository.RoleRepository;
import dev.mybike.mybike.security.JwtUtil;
import dev.mybike.mybike.service.EmailService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final RiderRepository riderRepository;

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, RiderRepository riderRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.riderRepository = riderRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        if (riderRepository.findByRidername(registerRequest.getRidername()).isPresent()) {
            return ResponseEntity.badRequest().body("Ridername is already taken");
        }

        if (riderRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email is already taken");
        }

        Rider newRider = new Rider();
        newRider.setRidername(registerRequest.getRidername());
        newRider.setEmail(registerRequest.getEmail());
        newRider.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        System.out.println(registerRequest.getEmail());


        String verificationCode = generateVerificationCode();
        newRider.setVerificationCode(verificationCode);
        newRider.setVerificationCodeExpiresAt(LocalDateTime.now().plusMinutes(15));
        newRider.setEnabled(false);

        Set<Role> roles = new HashSet<>();
        for (String roleName : registerRequest.getRoles()) {
            Role role = roleRepository.findByName(roleName)
                    .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
            roles.add(role);
        }
        newRider.setRoles(roles);

        riderRepository.save(newRider);
        emailService.sendVerificationEmail(newRider.getEmail(), verificationCode);

        return ResponseEntity.ok("Rider registered successfully. Please verify your email.");
    }
    /*
    {
        "ridername": "testuser",
        "password": "testpassword",
        "firstName": "John",
        "lastName": "Doe",
        "email": "himalgeethanjana18@gmail.com",
        "mobileNumber": "+1234567890",
        "roles": ["RIDER"]
    }
   */
    @PostMapping("/verify")
    public ResponseEntity<String> verifyRider(@RequestBody VerifyRequest verifyRequest) {
        Optional<Rider> riderOptional = riderRepository.findByVerificationCode(verifyRequest.getVerificationCode());

        if (riderOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid verification code");
        }

        Rider rider = riderOptional.get();
        if (rider.getVerificationCodeExpiresAt().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body("Verification code has expired");
        }

        rider.setEnabled(true);
        rider.setVerificationCode(null);
        rider.setVerificationCodeExpiresAt(null);
        riderRepository.save(rider);

        return ResponseEntity.ok("Account verified successfully");
    }
    /*
    {
        "verificationCode": "123456"
    }
    */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            // Attempt authentication
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getRidername(),
                            loginRequest.getPassword()
                    )
            );

            Rider rider = riderRepository.findByRidername(loginRequest.getRidername())
                    .orElseThrow(() -> new RuntimeException("Rider not found"));

            if (!rider.isEnabled()) {
                return ResponseEntity.badRequest().body("Account is not verified. Please verify your email.");
            }

            // Generate token after successful login
            String token = jwtUtil.generateToken(rider.getRidername());

            // FIXME: 
            String firstName = rider.getRidername();

            System.out.println(firstName);

            return ResponseEntity.ok(Map.of(
                    "message", "Login successful",
                    "token", token,
                    "expiresIn", jwtUtil.jwtExpirationMs(),
                    "firstName", firstName
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid ridername or password");
        }
    }
    /*
    {
        "ridername": "testuser",
        "password": "testpassword"
    }
    */

    @PostMapping("/resend-verification")
    public ResponseEntity<String> resendVerification(@RequestParam String email) {
        Optional<Rider> riderOptional = riderRepository.findByEmail(email);

        if (riderOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Rider not found");
        }

        Rider rider = riderOptional.get();
        if (rider.isEnabled()) {
            return ResponseEntity.badRequest().body("Account is already verified");
        }

        String newVerificationCode = generateVerificationCode();
        rider.setVerificationCode(newVerificationCode);
        rider.setVerificationCodeExpiresAt(LocalDateTime.now().plusMinutes(15));
        riderRepository.save(rider);
        emailService.sendVerificationEmail(email, newVerificationCode);

        return ResponseEntity.ok("Verification email sent");
    }

    private String generateVerificationCode() {
        Random random = new Random();
        return String.valueOf(random.nextInt(900000) + 100000);
    }
}


