package com.app.api.service;

import com.app.api.model.Analytics;
import com.app.api.model.Vehicle;
import com.app.api.repository.SparePartRepository;
import com.app.api.repository.VehicleRepository;
import com.app.api.repository.VehicleServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnalyticsService {
    public final VehicleRepository vehicleRepository;
    public final SparePartRepository sparePartRepository;
    public final VehicleServiceRepository vehicleServiceRepository;

    @Autowired
    public AnalyticsService(VehicleRepository vehicleRepository, SparePartRepository sparePartRepository, VehicleServiceRepository vehicleServiceRepository) {
        this.vehicleRepository = vehicleRepository;
        this.sparePartRepository = sparePartRepository;
        this.vehicleServiceRepository = vehicleServiceRepository;
    }

    public Analytics getAnalytics() {
        Analytics analytics = new Analytics();

        analytics.setVehicles(vehicleRepository.count());
        analytics.setSpareParts(sparePartRepository.count());
        analytics.setServices(sparePartRepository.count());
        return analytics;
    }
}
