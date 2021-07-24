package com.app.api.repository;

import com.app.api.model.VehicleServices;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleServiceRepository extends MongoRepository<VehicleServices, String> {

}
