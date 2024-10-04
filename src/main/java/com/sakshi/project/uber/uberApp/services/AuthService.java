package com.sakshi.project.uber.uberApp.services;

import com.sakshi.project.uber.uberApp.dto.DriverDto;
import com.sakshi.project.uber.uberApp.dto.SignupDto;
import com.sakshi.project.uber.uberApp.dto.UserDto;

public interface AuthService {
    String[] login(String email, String password);
    UserDto signup(SignupDto signupDto);
    DriverDto onboardNewDriver(Long userId, String vehicalId);

    String refreshToken(String refreshToken);
}
