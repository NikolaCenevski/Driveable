package com.wp.driveable.driveableapi.dto.requests;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {
    private String title;
    private String description;
    private int horsepower;
    private Integer manufacturingYear;
    private int mileage;
    private int price;
    private Boolean isNew;
    private List<String> carType;
    private String color;
    private String manufacturer;
    private String model;
    private List<String> images;
}
