package com.app.api.controller;


import com.app.api.model.Vehicle;
import com.app.api.service.VehicleService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class VehicleController {
    private final VehicleService vehicleService;

    @Autowired
    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }
    @PostMapping("/vehicle")
    public ResponseEntity<?> addVehicle(@RequestParam("model") String model,
                                        @RequestParam("email") String email,
                                        @RequestParam("contact") String contact,
                                        @RequestParam("location") String location,
                                        @RequestParam("price") String price,
                                        @RequestParam("transmission") String transmission,
                                        @RequestParam("fuelType") String fuelType,
                                        @RequestParam("year") String year,
                                        @RequestParam("condition") String condition,
                                        @RequestParam("category") String category,
                                        @RequestParam("seller") String seller,
                                        @RequestParam("manufacturer") String manufacturer,
                                        @RequestParam("engineCC") String engineCC,
                                        @RequestParam("moreInfo") String moreInfo,
                                        @RequestParam(value = "img1", required = false) MultipartFile img1,
                                        @RequestParam(value = "img2", required = false) MultipartFile img2,
                                        @RequestParam(value = "img3", required = false) MultipartFile img3,
                                        @RequestParam(value = "img4", required = false) MultipartFile img4)
    {
        Long unixTime = System.currentTimeMillis() / 1000L;
        LocalDate date = java.time.LocalDate.now();
        MultipartFile tempImg1 = null;
        MultipartFile tempImg2 = null;
        MultipartFile tempImg3 = null;
        MultipartFile tempImg4 = null;
        Vehicle tempOb = new Vehicle(unixTime.toString(),email, contact, location, price, model, transmission, fuelType, year, condition, category, seller, manufacturer, engineCC, moreInfo, date.toString(), null, null, null,null,"Pending","");
        if (img1 != null && !img1.isEmpty()) {
            String extension = FilenameUtils.getExtension(img1.getOriginalFilename());
            String img = unixTime+"-1" + "." + extension;
            tempImg1 = img1;
            tempOb.setImg1(img);
        }
        if (img2 != null && !img2.isEmpty()) {
            String extension = FilenameUtils.getExtension(img2.getOriginalFilename());
            String img = unixTime+"-2" + "." + extension;
            tempImg2 = img2;
            tempOb.setImg2(img);
        }
        if (img3 != null && !img3.isEmpty()) {
            String extension = FilenameUtils.getExtension(img3.getOriginalFilename());
            String img = unixTime+"-3" + "." + extension;
            tempImg3 = img3;
            tempOb.setImg3(img);
        }
        if (img4 != null && !img4.isEmpty()) {
            String extension = FilenameUtils.getExtension(img4.getOriginalFilename());
            String img = unixTime+"-4" + "." + extension;
            tempImg4 = img4;
            tempOb.setImg4(img);
        }
        return new ResponseEntity<>(vehicleService.addVehicle(tempOb, tempImg1, tempImg2, tempImg3, tempImg4), HttpStatus.CREATED);
    }

    @PutMapping("/vehicle")
    public ResponseEntity<?> updateVehicle(@RequestParam("id") String id,
                                           @RequestParam("model") String model,
                                           @RequestParam("email") String email,
                                           @RequestParam("contact") String contact,
                                           @RequestParam("location") String location,
                                           @RequestParam("price") String price,
                                           @RequestParam("transmission") String transmission,
                                           @RequestParam("fuelType") String fuelType,
                                           @RequestParam("year") String year,
                                           @RequestParam("condition") String condition,
                                           @RequestParam("category") String category,
                                           @RequestParam("seller") String seller,
                                           @RequestParam("manufacturer") String manufacturer,
                                           @RequestParam("engineCC") String engineCC,
                                           @RequestParam("moreInfo") String moreInfo,
                                           @RequestParam("date") String date,
                                           @RequestParam(value = "img1", required = false) MultipartFile img1,
                                           @RequestParam(value = "img2", required = false) MultipartFile img2,
                                           @RequestParam(value = "img3", required = false) MultipartFile img3,
                                           @RequestParam(value = "img4", required = false) MultipartFile img4)
    {
        MultipartFile tempImg1 = null;
        MultipartFile tempImg2 = null;
        MultipartFile tempImg3 = null;
        MultipartFile tempImg4 = null;
        Vehicle tempOb = new Vehicle(id,email, contact, location, price, model, transmission, fuelType, year, condition, category, seller, manufacturer, engineCC, moreInfo, date.toString(), null, null, null,null,"Pending","");
        if (img1 != null && !img1.isEmpty()) {
            String extension = FilenameUtils.getExtension(img1.getOriginalFilename());
            String img = id+"-1" + "." + extension;
            tempImg1 = img1;
            tempOb.setImg1(img);
        }
        if (img2 != null && !img2.isEmpty()) {
            String extension = FilenameUtils.getExtension(img2.getOriginalFilename());
            String img = id+"-2" + "." + extension;
            tempImg2 = img2;
            tempOb.setImg2(img);
        }
        if (img3 != null && !img3.isEmpty()) {
            String extension = FilenameUtils.getExtension(img3.getOriginalFilename());
            String img = id+"-3" + "." + extension;
            tempImg3 = img3;
            tempOb.setImg3(img);
        }
        if (img4 != null && !img4.isEmpty()) {
            String extension = FilenameUtils.getExtension(img4.getOriginalFilename());
            String img = id+"-4" + "." + extension;
            tempImg4 = img4;
            tempOb.setImg4(img);
        }
        return new ResponseEntity<>(vehicleService.updateVehicle(tempOb, tempImg1, tempImg2, tempImg3, tempImg4), HttpStatus.CREATED);
    }

    @GetMapping("/vehicle")
    public ResponseEntity<?> getAllVehicles() {
        return new ResponseEntity<>(vehicleService.getAllVehicles(), HttpStatus.OK);
    }

    @PostMapping("/vehicle/filter")
    public ResponseEntity<?> searchVehicle(@RequestBody Vehicle vehicle) {
        return new ResponseEntity<>(vehicleService.searchVehicle(vehicle), HttpStatus.OK);
    }

    @GetMapping("/vehicle/Pending")
    public ResponseEntity<?> getAllPendingVehicles() {
        return new ResponseEntity<>(vehicleService.getAllPendingVehicles(), HttpStatus.OK);
    }

    @GetMapping("/vehicle/id/{id}")
    public ResponseEntity<?> getVehicleById(@PathVariable String id) {
        return new ResponseEntity<>(vehicleService.getVehicleById(id),HttpStatus.OK);
    }

    @GetMapping("/vehicle/email/{email}")
    public ResponseEntity<?> getVehiclesByEmail(@PathVariable String email) {
        return new ResponseEntity<>(vehicleService.getVehiclesByEmail(email), HttpStatus.OK);
    }

    @PostMapping("/vehicle/review")
    public ResponseEntity<?> reviewVehicleSubmission(@RequestParam("id") String id,
                                     @RequestParam("status") String status,
                                     @RequestParam("comment") String comment)
    {
        return new ResponseEntity<>(vehicleService.reviewVehicleSubmission(id,status,comment), HttpStatus.OK);
    }

    @DeleteMapping("/vehicle/{id}")
    public ResponseEntity<?> deleteVehicle(@PathVariable String id) {
        return new ResponseEntity<>(vehicleService.deleteVehicle(id), HttpStatus.OK);
    }

    @GetMapping("/vehicle/report")
    public ResponseEntity<?> getReportData() {
        return new ResponseEntity<>(vehicleService.reportData(), HttpStatus.OK);
    }
}
