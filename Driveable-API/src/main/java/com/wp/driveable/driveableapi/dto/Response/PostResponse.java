package com.wp.driveable.driveableapi.dto.Response;
import com.wp.driveable.driveableapi.entity.Car;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostResponse{
    private String title;
    private String description;
    private int horsepower;
    private String manufacturingYear;
    private int price;
    private String name;
    private String surname;
    private String phoneNumber;
    private Boolean isNew;
    private String carType;
    private String color;
    private Car car;
}
