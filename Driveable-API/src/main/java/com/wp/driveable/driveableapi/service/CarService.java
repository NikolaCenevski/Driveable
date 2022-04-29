package com.wp.driveable.driveableapi.service;

import com.wp.driveable.driveableapi.entity.Car;
import com.wp.driveable.driveableapi.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarService {

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Car saveCar(String manufacturer, String model) {
        Car car = carRepository.findCarByManufacturerAndModel(manufacturer, model);
        if (car == null) {
            car = new Car();
            car.setModel(model);
            car.setManufacturer(manufacturer);
            return carRepository.save(car);
        }
        return car;
    }

    public List<String> getAllManufacturers() {
        return carRepository.findAll().stream().map(this::mapToManufacturer).distinct().collect(Collectors.toList());
    }
    public List<String> getAllModels(String manufacturer) {
        return carRepository.findAllByManufacturer(manufacturer).stream().map(this::mapToModel).collect(Collectors.toList());
    }
    public String mapToManufacturer(Car car)
    {
        return car.getManufacturer();
    }
    public String mapToModel(Car car)
    {
        return car.getModel();
    }
}
