package com.wp.driveable.driveableapi.service;

import com.wp.driveable.driveableapi.entity.CarType;
import com.wp.driveable.driveableapi.repository.CarTypeRepository;
import org.springframework.stereotype.Service;

@Service
public class CarTypeService {
    private final CarTypeRepository carTypeRepository;

    public CarTypeService(CarTypeRepository carTypeRepository) {
        this.carTypeRepository = carTypeRepository;
    }
    public void insertCarType(String type)
    {
        if (carTypeRepository.findByType(type)==null)
        {
            CarType carType= new CarType();
            carType.setType(type);
            carTypeRepository.save(carType);
        }
    }
}
