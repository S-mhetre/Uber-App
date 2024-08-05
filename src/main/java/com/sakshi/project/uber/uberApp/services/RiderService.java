package com.sakshi.project.uber.uberApp.services;

import com.sakshi.project.uber.uberApp.dto.DriverDto;
import com.sakshi.project.uber.uberApp.dto.RideDto;
import com.sakshi.project.uber.uberApp.dto.RideRequestDto;
import com.sakshi.project.uber.uberApp.dto.RiderDto;
import com.sakshi.project.uber.uberApp.entities.Rider;
import com.sakshi.project.uber.uberApp.entities.User;

import java.util.List;

public interface RiderService {

    RideRequestDto requestRide(RideRequestDto rideRequestDto);
    RideDto cancelRide(Long rideId);
    DriverDto rateRide(Long rideId, Double rating);
    RiderDto getMyProfile();
    List<RideDto> getAllMyRide();
    Rider createNewRider(User user);
    Rider getCurrentRider();
}
