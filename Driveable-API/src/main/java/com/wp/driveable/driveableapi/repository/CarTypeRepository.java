package com.wp.driveable.driveableapi.repository;

import com.wp.driveable.driveableapi.entity.CarType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarTypeRepository extends JpaRepository<CarType,Long> {
public CarType findByType(String carType);
}
