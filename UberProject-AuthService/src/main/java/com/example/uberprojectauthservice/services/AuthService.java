package com.example.uberprojectauthservice.services;

import com.example.uberprojectauthservice.dto.PassengerDto;
import com.example.uberprojectauthservice.dto.PassengerSignuprequestDto;
import com.example.uberprojectauthservice.models.Passenger;
import com.example.uberprojectauthservice.repositories.PassengerRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final PassengerRepository passengerRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthService(PassengerRepository passengerRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.passengerRepository = passengerRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public PassengerDto signupPassenger(PassengerSignuprequestDto passengerSignuprequestDto){
        Passenger passenger = Passenger.builder()
                .email(passengerSignuprequestDto.getEmail())
                .name(passengerSignuprequestDto.getName())
                .password(bCryptPasswordEncoder.encode(passengerSignuprequestDto.getPassword()))
                .phoneNumber(passengerSignuprequestDto.getPhoneNumber()).build();

        passengerRepository.save(passenger);
        return PassengerDto.from(passenger);

    }
}
