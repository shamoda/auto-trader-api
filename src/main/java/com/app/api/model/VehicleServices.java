package com.app.api.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Document
public class VehicleServices {

 @Id
 private String id;
 private String title;
 private String subTitle;
 private String contact;
 private String price;
 private String location;
 private String description;
 private String category;
 private String serviceProvider;
 private String image1;
 private String image2;
 private String image3;
 private LocalDateTime currentTime;
 private String comment;
 private String status;


}

