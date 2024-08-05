package com.sakshi.project.uber.uberApp.strategies;

import com.sakshi.project.uber.uberApp.dto.RideRequestDto;
import com.sakshi.project.uber.uberApp.entities.Driver;
import com.sakshi.project.uber.uberApp.entities.RideRequest;

import java.util.List;

public interface DriverMatchingStrategy {

    List<Driver> findMatchingDriver(RideRequest rideRequest);
}
