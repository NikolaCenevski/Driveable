package com.wp.driveable.driveableapi.dto.Response;

import com.wp.driveable.driveableapi.entity.Car;
import com.wp.driveable.driveableapi.entity.Reason;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportPostResponse implements Response {
    private long id;
    private long postId;
    private String title;
    private String description;
    private int horsepower;
    private Integer manufacturingYear;
    private int price;
    private int mileage;
    private LocalDate date;
    private String name;
    private String surname;
    private String phoneNumber;
    private Boolean isNew;
    private List<String> carType;
    private String color;
    private Car car;
    private int numOfImages;
    private List<ReasonResponse> reasons;
}
