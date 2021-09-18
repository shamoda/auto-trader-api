package com.app.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VehicleServiceDto {
//test
    private String title;
    private String subTitle;
    private String contactNo;
    private String price;
    private String description;
    private String category;
    private String location;
    private String serviceProvider;
    private LocalDateTime date;
    private MultipartFile image1;
    private MultipartFile image2;
    private MultipartFile image3;


}
