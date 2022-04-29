package com.wp.driveable.driveableapi.repository;

import com.wp.driveable.driveableapi.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car,Long> {
    public Car findCarByManufacturerAndModel(String Manufacturer,String Model);
    public List<Car> findAllByManufacturer(String Manufacturer);
}
