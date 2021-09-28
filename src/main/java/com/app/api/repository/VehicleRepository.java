package com.app.api.repository;

import com.app.api.model.Vehicle;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends MongoRepository<Vehicle, String> {
    List<Vehicle> findByEmail(String email);

    List<Vehicle> findByStatus(String Pending);
}
