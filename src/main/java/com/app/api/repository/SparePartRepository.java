package com.app.api.repository;

import com.app.api.model.SparePart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SparePartRepository extends MongoRepository<SparePart, String> {
    List<SparePart> findByEmail(String email);

    List<SparePart> findByStatus(String pending);
}
