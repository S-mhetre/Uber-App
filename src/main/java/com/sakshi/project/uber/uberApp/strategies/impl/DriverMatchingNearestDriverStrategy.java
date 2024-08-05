package com.sakshi.project.uber.uberApp.strategies.impl;

import com.sakshi.project.uber.uberApp.dto.RideRequestDto;
import com.sakshi.project.uber.uberApp.entities.Driver;
import com.sakshi.project.uber.uberApp.entities.RideRequest;
import com.sakshi.project.uber.uberApp.repositories.DriverRepository;
import com.sakshi.project.uber.uberApp.strategies.DriverMatchingStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DriverMatchingNearestDriverStrategy implements DriverMatchingStrategy {

    private final DriverRepository driverRepository;

    @Override
    public List<Driver> findMatchingDriver(RideRequest rideRequest) {
        return driverRepository.findTenNearestDriver(rideRequest.getPickupLocation());
    }
}
