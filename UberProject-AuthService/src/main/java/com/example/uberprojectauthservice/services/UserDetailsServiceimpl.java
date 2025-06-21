package com.example.uberprojectauthservice.services;

import com.example.uberprojectauthservice.helper.AuthPassengerDetails;
import com.example.uberprojectauthservice.models.Passenger;
import com.example.uberprojectauthservice.repositories.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
//This class is responsible for loading the passenger in the form of userdetails obj. for auth
@Service
public class UserDetailsServiceimpl implements UserDetailsService {
   @Autowired
    private  PassengerRepository passengerRepository;



    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Passenger> passenger=passengerRepository.findPassengerByEmail(email);
        if(passenger.isPresent()){
            return new AuthPassengerDetails(passenger.get());
        }
        else{
            throw new UsernameNotFoundException("Can't find the Passenger by given Email");
        }
    }
}
