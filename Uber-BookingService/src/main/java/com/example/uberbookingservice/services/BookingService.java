package com.example.uberbookingservice.services;


import com.example.uberbookingservice.dto.CreateBooikngDto;
import com.example.uberbookingservice.dto.CreateBookingResponseDto;
import com.example.uberproject_entityservice.models.Booking;

public interface BookingService {

    public CreateBookingResponseDto createBooking(CreateBooikngDto createBooikngDto);
}
