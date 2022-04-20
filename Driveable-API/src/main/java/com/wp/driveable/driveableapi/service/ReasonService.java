package com.wp.driveable.driveableapi.service;

import com.wp.driveable.driveableapi.repository.ReasonRepository;
import org.springframework.stereotype.Service;

@Service
public class ReasonService{
    private final ReasonRepository reasonRepository;

    public ReasonService(ReasonRepository reasonRepository) {
        this.reasonRepository = reasonRepository;
    }
}
