package dev.mybike.mybike.repository;

import java.util.Optional;

import dev.mybike.mybike.model.Rider;
import org.springframework.data.mongodb.repository.MongoRepository;

import dev.mybike.mybike.model.Role;

public interface RoleRepository extends MongoRepository<Role,String> {
    Optional<Role> findByName(String name);
}