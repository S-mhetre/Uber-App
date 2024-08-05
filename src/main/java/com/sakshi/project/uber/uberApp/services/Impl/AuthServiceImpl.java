package com.sakshi.project.uber.uberApp.services.Impl;

import com.sakshi.project.uber.uberApp.dto.DriverDto;
import com.sakshi.project.uber.uberApp.dto.SignupDto;
import com.sakshi.project.uber.uberApp.dto.UserDto;
import com.sakshi.project.uber.uberApp.entities.Rider;
import com.sakshi.project.uber.uberApp.entities.User;
import com.sakshi.project.uber.uberApp.entities.enums.Role;
import com.sakshi.project.uber.uberApp.exceptions.RuntimeConflictException;
import com.sakshi.project.uber.uberApp.repositories.UserRepository;
import com.sakshi.project.uber.uberApp.services.AuthService;
import com.sakshi.project.uber.uberApp.services.RiderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final RiderService riderService;

    @Override
    public String login(String email, String password) {
        return "";
    }

    @Override
    @Transactional
    public UserDto signup(SignupDto signupDto) {
        User user = userRepository.findByEmail(signupDto.getEmail()).orElse(null);
        if(user != null)
            throw new RuntimeConflictException("Cannot signup, user already exists with two mails"+signupDto.getEmail());

        User mappedUser = modelMapper.map(signupDto, User.class);
        mappedUser.setRoles(Set.of(Role.RIDER));
        User savedUser = userRepository.save(mappedUser);

        riderService.createNewRider(savedUser);

        //TODO add wallet related service here
        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public DriverDto onboardNewDriver(Long userId) {
        return null;
    }
}
