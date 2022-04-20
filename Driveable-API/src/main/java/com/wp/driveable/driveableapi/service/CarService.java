package com.wp.driveable.driveableapi.service;

import com.wp.driveable.driveableapi.entity.Car;
import com.wp.driveable.driveableapi.repository.CarRepository;
import org.springframework.stereotype.Service;

@Service
public class CarService {
    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }
    public Car saveCar(String manufacturer, String model)
    {
        Car car=carRepository.findCarByManufacturerAndModel(manufacturer,model);
        if (car==null)
        {
            car=new Car();
            car.setModel(model);
            car.setManufacturer(manufacturer);
            return carRepository.save(car);
        }
        return car;
    }
}
