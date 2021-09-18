package com.app.api.controller;

import com.app.api.common.constant.ServiceConstant;
import com.app.api.dto.VehicleServiceDto;
import com.app.api.model.SparePart;
import com.app.api.model.VehicleServices;
import com.app.api.service.VehicleWorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class VehicleServiceController {

    private final VehicleWorkerService vehicleWorkerService;

    @Autowired
    public VehicleServiceController(VehicleWorkerService vehicleWorkerService){
        this.vehicleWorkerService = vehicleWorkerService;
    }

    @PostMapping("/services")
    public ResponseEntity<?> insertService(  @RequestParam("title") String title,
                                             @RequestParam("subtitle") String subTitle,
                                             @RequestParam("contactNo") String contactNo,
                                             @RequestParam("price")String price,
                                             @RequestParam("description") String description,
                                             @RequestParam("category") String category,
                                             @RequestParam("location") String location,
                                             @RequestParam("provider") String serviceProvider,
                                             @RequestParam(value="image1") MultipartFile image1,
                                             @RequestParam(value = "image2",required = false )MultipartFile image2,
                                             @RequestParam(value = "image3",required = false )MultipartFile image3
    )


    {
        VehicleServices services = new VehicleServices(null,title,subTitle,contactNo,price,location,description,category,serviceProvider,null,null,null,LocalDateTime.now(),"", ServiceConstant.SERVICE_STATUS);
        return new ResponseEntity<>(vehicleWorkerService.insertService(services,image1,image2,image3), HttpStatus.CREATED);
    }

    @GetMapping("/services")
    public ResponseEntity<?> getAllServices() {
        return new ResponseEntity<>(vehicleWorkerService.getServices(), HttpStatus.OK);
    }

    @GetMapping("/services/{Id}")
    public ResponseEntity<?> getServiceById(@PathVariable String Id){
        return new ResponseEntity<>(vehicleWorkerService.getServiceById(Id),HttpStatus.OK);
    }

    @PutMapping("/services/review/{id}")
    public ResponseEntity<?> reviewService(@PathVariable String id ,
                                           @RequestParam("comment") String comment,
                                           @RequestParam("status") String status
    )

    {
        return new ResponseEntity<>(vehicleWorkerService.reviewService(id,status,comment),HttpStatus.OK);
    }

    @PutMapping("/services/{id}")
    public ResponseEntity<?> updateService(@PathVariable String id,
                                           @RequestParam("title") String title,
                                           @RequestParam("subtitle") String subTitle,
                                           @RequestParam("contactNo") String contactNo,
                                           @RequestParam("description") String description,
                                           @RequestParam("category") String category,
                                           @RequestParam("price") String price,
                                           @RequestParam("location") String location,
                                           @RequestParam("provider") String serviceProvider,
                                           @RequestParam(value="image1",required = false) MultipartFile image1,
                                           @RequestParam(value = "image2",required = false )MultipartFile image2,
                                           @RequestParam(value = "image3",required = false )MultipartFile image3
    )
    {

        VehicleServiceDto serviceDto = new VehicleServiceDto(title,subTitle,contactNo,description,category,location,price,serviceProvider,LocalDateTime.now(),image1,image2,image3);
        return new ResponseEntity<>(vehicleWorkerService.updateService(id,serviceDto), HttpStatus.CREATED);
    }

    @PostMapping("/services/search")
    public ResponseEntity<?> getByCategory(@RequestBody VehicleServices vehicleServices) {
        return new ResponseEntity<>(vehicleWorkerService.retrieveByCategory(vehicleServices), HttpStatus.OK);
    }

    @DeleteMapping("/services/{id}")
    public ResponseEntity<?> deleteService(@PathVariable String id) {
        return new ResponseEntity<>(vehicleWorkerService.deleteService(id), HttpStatus.OK);
    }

}