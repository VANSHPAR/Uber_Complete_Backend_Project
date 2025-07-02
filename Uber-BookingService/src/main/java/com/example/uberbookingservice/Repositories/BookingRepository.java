package com.example.uberbookingservice.Repositories;

import com.example.uberproject_entityservice.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
}
