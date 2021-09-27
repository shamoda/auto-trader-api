package com.app.api.service;

import com.app.api.model.Revenue;
import com.app.api.repository.RevenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RevenueService {
    private final RevenueRepository repository;

    @Autowired
    public RevenueService(RevenueRepository repository) {
        this.repository = repository;
    }

    public Revenue insertRevenue(Revenue revenue) {
        return repository.save(revenue);
    }

    public List<Revenue> getAllRevenue() {
        return repository.findAll();
    }

    public Revenue getRevenueById(String id) {
        return repository.findById(id).get();
    }

    public String deleteById(String id) {
        repository.deleteById(id);
        return "Revenue record deleted successfully";
    }
}
