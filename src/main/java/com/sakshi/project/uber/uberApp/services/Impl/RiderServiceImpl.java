package com.sakshi.project.uber.uberApp.services.Impl;

import com.sakshi.project.uber.uberApp.dto.DriverDto;
import com.sakshi.project.uber.uberApp.dto.RideDto;
import com.sakshi.project.uber.uberApp.dto.RideRequestDto;
import com.sakshi.project.uber.uberApp.dto.RiderDto;
import com.sakshi.project.uber.uberApp.entities.RideRequest;
import com.sakshi.project.uber.uberApp.entities.Rider;
import com.sakshi.project.uber.uberApp.entities.User;
import com.sakshi.project.uber.uberApp.entities.enums.RideRequestStatus;
import com.sakshi.project.uber.uberApp.exceptions.ResourceNotFoundException;
import com.sakshi.project.uber.uberApp.repositories.RideRequestRepository;
import com.sakshi.project.uber.uberApp.repositories.RiderRepository;
import com.sakshi.project.uber.uberApp.services.RiderService;
import com.sakshi.project.uber.uberApp.strategies.DriverMatchingStrategy;
import com.sakshi.project.uber.uberApp.strategies.RideFareCalculationStrategy;
import com.sakshi.project.uber.uberApp.strategies.RideStrategyManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RiderServiceImpl implements RiderService {

    private final ModelMapper modelMapper;
    private final RideStrategyManager rideStrategyManager;
    private final RideRequestRepository rideRequestRepository;
    private final RiderRepository riderRepository;

    @Override
    public RideRequestDto requestRide(RideRequestDto rideRequestDto) {
        Rider rider = getCurrentRider();
        RideRequest rideRequest = modelMapper.map(rideRequestDto, RideRequest.class);
//        log.info(rideRequest.toString());
        rideRequest.setRideRequestStatus(RideRequestStatus.PENDING);
         Double fare = rideStrategyManager.rideFareCalculationStrategy().calculateFare(rideRequest);
         rideRequest.setFare(fare);

         RideRequest savedRideRequest = rideRequestRepository.save(rideRequest);
         rideStrategyManager.driverMatchingStrategy(rider.getRatings()).findMatchingDriver(rideRequest);
        return modelMapper.map(savedRideRequest, RideRequestDto.class);
    }

    @Override
    public RideDto cancelRide(Long rideId) {
        return null;
    }

    @Override
    public DriverDto rateRide(Long rideId, Double rating) {
        return null;
    }

    @Override
    public RiderDto getMyProfile() {
        return null;
    }

    @Override
    public List<RideDto> getAllMyRide() {
        return List.of();
    }

    @Override
    public Rider createNewRider(User user) {
        Rider rider = Rider
                .builder()
                .user(user)
                .ratings(0.0)
                .build();
        return riderRepository.save(rider);
    }

    @Override
    public Rider getCurrentRider() {
    // TODO : implement Spring security
        return riderRepository.findById(1L).orElseThrow(() -> new ResourceNotFoundException(
                "Rider not found with id "+1
        ));
    }
}
