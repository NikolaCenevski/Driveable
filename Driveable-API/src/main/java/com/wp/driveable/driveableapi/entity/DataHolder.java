package com.wp.driveable.driveableapi.entity;

import com.wp.driveable.driveableapi.service.CarTypeService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DataHolder {
    private final CarTypeService carTypeService;

    public DataHolder(CarTypeService carTypeService) {
        this.carTypeService = carTypeService;
    }
    @PostConstruct
    public void init()
    {
        carTypeService.insertCarType("CAR");
        carTypeService.insertCarType("SEDAN");
        carTypeService.insertCarType("COUPE");
        carTypeService.insertCarType("SPORTS CAR");
        carTypeService.insertCarType("HATCHBACK");
        carTypeService.insertCarType("CONVERTIBLE");
        carTypeService.insertCarType("SPORT-UTILITY VEHICLE (SUV)");
        carTypeService.insertCarType("MINIVAN");
        carTypeService.insertCarType("PICKUP TRUCK");
        carTypeService.insertCarType("TRUCK");
        carTypeService.insertCarType("MOTORCYCLE");
        carTypeService.insertCarType("STANDARD MOTORCYCLE");
        carTypeService.insertCarType("CRUISER MOTORCYCLE");
        carTypeService.insertCarType("SPORT MOTORCYCLE");
    }
}
