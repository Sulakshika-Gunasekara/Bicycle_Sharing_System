package dev.mybike.mybike.service.impl;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dev.mybike.mybike.model.Rider;
import dev.mybike.mybike.service.RiderService;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final RiderService riderService;

    // Use @Lazy here to avoid circular dependency issues
    @Autowired
    public CustomUserDetailsService(@Lazy RiderService riderService) {
        this.riderService = riderService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Rider rider = riderService.loadRiderByUsername(username);
        if (rider == null) {
            throw new UsernameNotFoundException("Rider not found");
        }
        return new User(rider.getUsername(), rider.getPassword(), rider.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList()));
    }
}