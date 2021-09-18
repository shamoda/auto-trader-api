package com.app.api.service;

import com.app.api.dto.VehicleServiceDto;
import com.app.api.exception.VehicleServicesNotFoundException;
import com.app.api.model.VehicleServices;
import com.app.api.repository.VehicleServiceRepository;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.app.api.common.constant.ServiceConstant.*;

@Service
public class VehicleWorkerService {

    private final VehicleServiceRepository vehicleServiceRepository;
    private final MongoTemplate mongoTemplate;
    private final FileService fileService;

    @Autowired
    public VehicleWorkerService(VehicleServiceRepository vehicleServiceRepository, MongoTemplate mongoTemplate, FileService fileService) {
        this.vehicleServiceRepository = vehicleServiceRepository;
        this.mongoTemplate = mongoTemplate;
        this.fileService = fileService;
    }

    public VehicleServices insertService(VehicleServices services, MultipartFile image1, MultipartFile image2, MultipartFile image3) {

        Long unixTime = System.currentTimeMillis() / 1000L;
        services.setId("AT" +unixTime.toString());
        //upload and set image
        if(image1 != null && !image1.isEmpty()){
            services.setImage1(imageUploader(image1,services.getTitle(),services.getImage1()));
        }
        if(image2 != null && !image2.isEmpty()){
            services.setImage2(imageUploader(image2,services.getTitle(),services.getImage2()));
        }
        if(image3 != null && !image3.isEmpty()){
            services.setImage3(imageUploader(image3,services.getTitle(),services.getImage3()));
        }
        return vehicleServiceRepository.save(services);
    }

    public List<VehicleServices> getServices() {
        return vehicleServiceRepository.findAll();
    }

    public VehicleServices reviewService(String id,String status, String comment){
        return vehicleServiceRepository.findById(id)
                .map(service -> {
                    service.setComment(comment);
                    service.setStatus(status);
                    return vehicleServiceRepository.save(service);
                }).orElseGet(() -> {
                    throw new VehicleServicesNotFoundException(id);
                });
    }

    public VehicleServices getServiceById(String id) {
        return vehicleServiceRepository.findById(id)
                .orElseThrow( () -> new VehicleServicesNotFoundException(id));
    }

    public List<VehicleServices> retrieveByCategory(VehicleServices vehicleServices) {
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase().withMatcher("title", ExampleMatcher.GenericPropertyMatcher.of(ExampleMatcher.StringMatcher.CONTAINING));
        Example<VehicleServices> example = Example.of(vehicleServices,matcher);
        return  vehicleServiceRepository.findAll(example);

    }

    public String deleteService(String id) {
        VehicleServices vehicleServices = vehicleServiceRepository.findById(id).get();
        String image1 = vehicleServices.getImage1();
        String image2 = vehicleServices.getImage2();
        String image3  = vehicleServices.getImage3();

        deleteImage(image1);
        deleteImage(image2);
        deleteImage(image3);
        vehicleServiceRepository.deleteById(id);
        return "Service "+id+"Deleted";
    }

    public VehicleServices updateService(String Id ,VehicleServiceDto serviceDto) {
        return vehicleServiceRepository.findById(Id)
                .map(service -> {
                    service.setTitle(serviceDto.getTitle());
                    service.setSubTitle(serviceDto.getSubTitle());
                    service.setContact(serviceDto.getContactNo());
                    service.setDescription(serviceDto.getDescription());
                    service.setCategory(serviceDto.getCategory());
                    service.setLocation(serviceDto.getLocation());
                    service.setServiceProvider(serviceDto.getServiceProvider());
                    service.setPrice(serviceDto.getPrice());
                    service.setStatus("pending");
                    //delete & upload previous images
                    MultipartFile img1 = serviceDto.getImage1();
                    MultipartFile img2 = serviceDto.getImage2();
                    MultipartFile img3 = serviceDto.getImage3();
                    //test1
                    if(img1 != null){
                        service.setImage1(imageUploader(img1,serviceDto.getTitle(),service.getImage1()));
                    }
                    if(img2 != null){
                        service.setImage2(imageUploader(img2,serviceDto.getTitle(),service.getImage2()));
                    }
                    if(img3 != null){
                        service.setImage3(imageUploader(img3,serviceDto.getTitle(),service.getImage3()));
                    }

                    return vehicleServiceRepository.save(service);
                })
                .orElseGet(() -> {
                    throw new VehicleServicesNotFoundException(Id);
                });
    }


    private String imageUploader(MultipartFile image,String title,String imageName){
        //deleting previous image
        if(imageName != null){
            deleteImage(imageName);
        }
        String extension = FilenameUtils.getExtension(image.getOriginalFilename());
        Long unixTime = System.currentTimeMillis() / 1000L;
        String fileName = unixTime+ title +"."+extension;
        fileService.uploadFile(image,fileName,SERVICE_FILE_TYPE);
        return fileName;
    }

    private void deleteImage(String imageName){
        if(imageName != null ){
            fileService.deleteFile(imageName,SERVICE_FILE_TYPE);
        }
    }
}