package com.example.uberbookingservice.Repositories;

import com.example.uberproject_entityservice.models.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassengerRepository extends JpaRepository<Passenger, Integer> {
}
