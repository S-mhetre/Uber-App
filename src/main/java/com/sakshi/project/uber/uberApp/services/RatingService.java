package com.sakshi.project.uber.uberApp.services;

import com.sakshi.project.uber.uberApp.dto.DriverDto;
import com.sakshi.project.uber.uberApp.dto.RiderDto;
import com.sakshi.project.uber.uberApp.entities.Ride;

public interface RatingService {

    DriverDto rateDriver(Ride ride, Double rating);
    RiderDto rateRider(Ride ride, Double rating);

    void createNewRating(Ride ride);
}
