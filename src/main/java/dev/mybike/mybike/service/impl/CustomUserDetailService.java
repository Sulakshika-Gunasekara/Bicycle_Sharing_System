package dev.mybike.mybike.service.impl;

import dev.mybike.mybike.model.Role;
import dev.mybike.mybike.model.Rider;
import dev.mybike.mybike.repository.RiderRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CustomUserDetailService implements UserDetailsService {
    private final RiderRepository userRepository;
    public CustomUserDetailService(RiderRepository userRepository){
        this.userRepository=userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{

        Rider user = userRepository.findByRidername(username).orElseThrow(() -> new UsernameNotFoundException("Rider not found"+username));

        return new org.springframework.security.core.userdetails.User(user.getRidername(),user.getPassword(),user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getname())).collect(Collectors.toList()));
    }
}