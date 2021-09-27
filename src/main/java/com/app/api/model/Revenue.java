package com.app.api.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Document
public class Revenue {
    @Id
    private String id;
    private LocalDate date;
    private double revenue;
    private double expense;
    private double profit;
}
