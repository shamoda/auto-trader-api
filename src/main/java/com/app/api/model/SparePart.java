package com.app.api.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Document
public class SparePart {
    @Id
    private String id;
    private String email;
    private String contact;
    private String seller;
    private String location;
    private String status;
    private String comment;
    private String date;
    private String title;
    private String price;
    private String condition;
    private String type;
    private String category;
    private String additionalInfo;
    private String img1;
    private String img2;
    private String img3;
}
