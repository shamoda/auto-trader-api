package com.app.api.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Document
public class Vehicle {
    @Id
    private String id;
    private String email;
    private String contact;
    private String location;
    private String price;
    private String model;
    private String transmission;
    private String fuelType;
    private String year;
    private String condition;
    private String category;
    private String seller;
    private String manufacturer;
    private String engineCC;
    private String moreInfo;
    private String date;
    private String img1;
    private String img2;
    private String img3;
    private String img4;
    private String status;
    private String comment;
}
