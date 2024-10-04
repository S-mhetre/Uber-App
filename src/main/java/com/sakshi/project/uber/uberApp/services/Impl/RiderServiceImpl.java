package com.sakshi.project.uber.uberApp.services.Impl;

import com.sakshi.project.uber.uberApp.dto.DriverDto;
import com.sakshi.project.uber.uberApp.dto.RideDto;
import com.sakshi.project.uber.uberApp.dto.RideRequestDto;
import com.sakshi.project.uber.uberApp.dto.RiderDto;
import com.sakshi.project.uber.uberApp.entities.*;
import com.sakshi.project.uber.uberApp.entities.enums.RideRequestStatus;
import com.sakshi.project.uber.uberApp.entities.enums.RideStatus;
import com.sakshi.project.uber.uberApp.exceptions.ResourceNotFoundException;
import com.sakshi.project.uber.uberApp.repositories.RideRequestRepository;
import com.sakshi.project.uber.uberApp.repositories.RiderRepository;
import com.sakshi.project.uber.uberApp.services.DriverService;
import com.sakshi.project.uber.uberApp.services.RatingService;
import com.sakshi.project.uber.uberApp.services.RideService;
import com.sakshi.project.uber.uberApp.services.RiderService;
import com.sakshi.project.uber.uberApp.strategies.RideStrategyManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final RideService rideService;
    private final DriverService driverService;
    private final RatingService ratingService;

    @Override
    public RideRequestDto requestRide(RideRequestDto rideRequestDto) {
        Rider rider = getCurrentRider();
        RideRequest rideRequest = modelMapper.map(rideRequestDto, RideRequest.class);
        rideRequest.setRider(rider);
//        log.info(rideRequest.toString());
        rideRequest.setRideRequestStatus(RideRequestStatus.PENDING);
         Double fare = rideStrategyManager.rideFareCalculationStrategy().calculateFare(rideRequest);
         rideRequest.setFare(fare);

         RideRequest savedRideRequest = rideRequestRepository.save(rideRequest);

         List<Driver> drivers = rideStrategyManager
                 .driverMatchingStrategy(rider.getRating()).findMatchingDriver(rideRequest);
         // TODO : Send notification to all the drivers about this ride request
        return modelMapper.map(savedRideRequest, RideRequestDto.class);
    }

    @Override
    public RideDto cancelRide(Long rideId) {
        Rider rider = getCurrentRider();
        Ride ride = rideService.getRideById(rideId);

        if(!rider.equals(ride.getRider())){
            throw new RuntimeException("Ride does not own this ride with id: "+rideId);
        }

        if(!ride.getRideStatus().equals(RideStatus.CONFIRMED)){
            throw new RuntimeException("invalid status: "+ride.getRideStatus()+" Ride cannot be cancelled");
        }

        Ride savedRide = rideService.updateRideStatus(ride, RideStatus.CONFIRMED);
        driverService.updateDriverAvailability(ride.getDriver(), true);
        return modelMapper.map(savedRide, RideDto.class);
    }

    @Override
    public DriverDto rateDriver(Long rideId, Double rating) {
        Ride ride =rideService.getRideById(rideId);
        Rider rider =getCurrentRider();

        if(!rider.equals(ride.getRider())){
            throw new RuntimeException("Rider can not rate this ride because he is not the owner of this ride");
        }

        if(!ride.getRideStatus().equals(RideStatus.ENDED)){
            throw new RuntimeException("Ride status is not Ended hence cannot be rated, status: "+ride.getRideStatus());
        }

        return ratingService.rateDriver(ride, rating);
    }

    @Override
    public RiderDto getMyProfile() {
        Rider curretRider = getCurrentRider();
        return  modelMapper.map(curretRider, RiderDto.class);
    }

    @Override
    public Page<RideDto> getAllMyRide(PageRequest pageRequest) {
        Rider curretRider = getCurrentRider();
        return rideService.getAllRidesOfRider(curretRider, pageRequest).map(
                ride -> modelMapper.map(ride, RideDto.class)
        );
    }

    @Override
    public Rider createNewRider(User user) {
        Rider rider = Rider
                .builder()
                .user(user)
                .rating(0.0)
                .build();
        return riderRepository.save(rider);
    }

    @Override
    public Rider getCurrentRider() {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return riderRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException(
                "Rider not associated with user with id "+user.getId()
        ));
    }
}
