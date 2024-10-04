package com.sakshi.project.uber.uberApp.services.Impl;

import com.sakshi.project.uber.uberApp.entities.RideRequest;
import com.sakshi.project.uber.uberApp.exceptions.ResourceNotFoundException;
import com.sakshi.project.uber.uberApp.repositories.RideRequestRepository;
import com.sakshi.project.uber.uberApp.services.RideRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RideRequestServiceImpl implements RideRequestService {

    private final RideRequestRepository rideRequestRepository;

    @Override
    public RideRequest findRideRequestById(Long rideRequestId) {
        return rideRequestRepository.findById(rideRequestId)
                .orElseThrow(() -> new ResourceNotFoundException("Ride Request not found with id: "+rideRequestId));
    }

    @Override
    public void update(RideRequest rideRequest) {
        rideRequestRepository.findById(rideRequest.getId())
                .orElseThrow(() -> new ResourceNotFoundException("RideRequest not found by Id: "+rideRequest.getId()));
        rideRequestRepository.save(rideRequest);
    }
}
