package com.wp.driveable.driveableapi.repository;

import com.wp.driveable.driveableapi.entity.Reason;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReasonRepository extends JpaRepository<Reason,Long> {
}
