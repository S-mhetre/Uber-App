package com.sakshi.project.uber.uberApp.services;

import com.sakshi.project.uber.uberApp.dto.DriverDto;
import com.sakshi.project.uber.uberApp.dto.RideDto;
import com.sakshi.project.uber.uberApp.dto.RideRequestDto;
import com.sakshi.project.uber.uberApp.dto.RiderDto;
import com.sakshi.project.uber.uberApp.entities.Rider;
import com.sakshi.project.uber.uberApp.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface RiderService {

    RideRequestDto requestRide(RideRequestDto rideRequestDto);
    RideDto cancelRide(Long rideId);
    DriverDto rateDriver(Long rideId, Double rating);
    RiderDto getMyProfile();
    Page<RideDto> getAllMyRide(PageRequest pageRequest);
    Rider createNewRider(User user);
    Rider getCurrentRider();
}
