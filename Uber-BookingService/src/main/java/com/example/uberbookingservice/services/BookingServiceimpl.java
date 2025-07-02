package com.example.uberbookingservice.services;

import com.example.uberbookingservice.Repositories.BookingRepository;
import com.example.uberbookingservice.Repositories.PassengerRepository;
import com.example.uberbookingservice.dto.CreateBooikngDto;
import com.example.uberbookingservice.dto.CreateBookingResponseDto;
import com.example.uberproject_entityservice.models.Booking;
import com.example.uberproject_entityservice.models.BookingStatus;
import com.example.uberproject_entityservice.models.Passenger;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookingServiceimpl implements BookingService{


    private final PassengerRepository passengerRepository;
    private final BookingRepository bookingRepository;

    public BookingServiceimpl(PassengerRepository passengerRepository, BookingRepository bookingRepository) {
        this.passengerRepository = passengerRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public CreateBookingResponseDto createBooking(CreateBooikngDto createBooikngDto) {
        Optional<Passenger> passenger=passengerRepository.findById(Math.toIntExact(createBooikngDto.getPassengerId()));
        Booking  booking = Booking.builder()
                .bookingStatus(BookingStatus.ASSIGNING_DRIVER)
                .startLocation(createBooikngDto.getStartLocation())
                .endLocation(createBooikngDto.getEndLocation())
                .passenger(passenger.get())
                .build();
        Booking newBooking=bookingRepository.save(booking);
        return CreateBookingResponseDto.builder()
                .bookingId(newBooking.getId())
                .bookingStatus(newBooking.getBookingStatus().toString())
                .driver(Optional.of(newBooking.getDriver())).build();
    }
}
