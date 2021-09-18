package com.app.api.service;

import com.app.api.exception.VehicleNotFoundException;
import com.app.api.model.Vehicle;
import com.app.api.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class VehicleService {
    private final VehicleRepository vehicleRepository;
    private final FileService fileService;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public VehicleService(VehicleRepository vehicleRepository, FileService fileService, MongoTemplate mongoTemplate) {
        this.vehicleRepository = vehicleRepository;
        this.fileService = fileService;
        this.mongoTemplate = mongoTemplate;
    }

    public Vehicle addVehicle(Vehicle vehicle, MultipartFile img1, MultipartFile img2, MultipartFile img3,
                              MultipartFile img4) {
        if (img1 != null && !img1.isEmpty()) {
            fileService.uploadFile(img1, vehicle.getImg1(), "vehicle");
        }
        if (img2 != null && !img2.isEmpty()) {
            fileService.uploadFile(img2, vehicle.getImg2(), "vehicle");
        }
        if (img3 != null && !img3.isEmpty()) {
            fileService.uploadFile(img3, vehicle.getImg3(), "vehicle");
        }
        if (img4 != null && !img4.isEmpty()) {
            fileService.uploadFile(img3, vehicle.getImg4(), "vehicle");
        }
        return vehicleRepository.save(vehicle);
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public Vehicle getVehicleById(String id) throws VehicleNotFoundException {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new VehicleNotFoundException(id));
    }

    public List<Vehicle> getVehiclesByEmail(String email) {
        return vehicleRepository.findByEmail(email);
    }

    public List<Vehicle> getAllPendingVehicles() {
        return vehicleRepository.findByStatus("Pending");
    }

    public Vehicle updateVehicle(Vehicle vehicle,MultipartFile img1, MultipartFile img2, MultipartFile img3,
                                 MultipartFile img4 ){
        Vehicle tempObject = vehicleRepository.findById(vehicle.getId()).get();
        if (tempObject.getImg1() != null)
            fileService.deleteFile(tempObject.getImg1(), "vehicle");
        if (tempObject.getImg2() != null)
            fileService.deleteFile(tempObject.getImg2(), "vehicle");
        if (tempObject.getImg3() != null)
            fileService.deleteFile(tempObject.getImg3(), "vehicle");
        if (tempObject.getImg4() != null)
            fileService.deleteFile(tempObject.getImg4(), "vehicle");

        if (img1 != null && !img1.isEmpty()) {
            fileService.uploadFile(img1, vehicle.getImg1(), "vehicle");
        }
        if (img2 != null && !img2.isEmpty()) {
            fileService.uploadFile(img2, vehicle.getImg2(), "vehicle");
        }
        if (img3 != null && !img3.isEmpty()) {
            fileService.uploadFile(img3, vehicle.getImg3(), "vehicle");
        }
        if (img4 != null && !img4.isEmpty()) {
            fileService.uploadFile(img4, vehicle.getImg4(), "vehicle");
        }
        return vehicleRepository.save(vehicle);
    }

    public String deleteVehicle(String id) {
        Vehicle vehicle = vehicleRepository.findById(id).get();
        String img1 = vehicle.getImg1();
        String img2 = vehicle.getImg2();
        String img3 = vehicle.getImg3();
        String img4 = vehicle.getImg4();
        if (img1 != null) {
            fileService.deleteFile(img1, "vehicle");
        }
        if (img2 != null) {
            fileService.deleteFile(img2, "vehicle");
        }
        if (img3 != null) {
            fileService.deleteFile(img3, "vehicle");
        }
        if (img4 != null) {
            fileService.deleteFile(img4, "vehicle");
        }
        vehicleRepository.deleteById(id);
        return "Vehicle " +id+ " Deleted Successfully.";
    }

    public List<Vehicle> searchVehicle(Vehicle vehicle) {
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase().withMatcher("model", ExampleMatcher.GenericPropertyMatcher.of(ExampleMatcher.StringMatcher.CONTAINING));
        Example<Vehicle> example = Example.of(vehicle, matcher);
        return vehicleRepository.findAll(example);
    }

    public String reviewVehicleSubmission(String id, String status, String comment) {
        Vehicle vehicle = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(id)), Vehicle.class);
        vehicle.setStatus(status);
        vehicle.setComment(comment);
        mongoTemplate.save(vehicle);
        return "Reviewed Successfully";
    }

    public List<Vehicle> reportData() {
        return vehicleRepository.findAll(Sort.by(Sort.Direction.DESC, "date"));
    }
}
