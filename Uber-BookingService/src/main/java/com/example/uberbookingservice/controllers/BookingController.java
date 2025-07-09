package com.example.uberbookingservice.controllers;

import com.example.uberbookingservice.dto.CreateBooikngDto;
import com.example.uberbookingservice.dto.CreateBookingResponseDto;
import com.example.uberbookingservice.dto.UpdateBookingRequestDto;
import com.example.uberbookingservice.dto.UpdateBookingResponseDto;
import com.example.uberbookingservice.services.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/booking")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<CreateBookingResponseDto> createBooking(@RequestBody CreateBooikngDto createBooikngDto) {

        return new ResponseEntity<>(bookingService.createBooking(createBooikngDto), HttpStatus.CREATED);
    }

    @PostMapping("/{driverId}")
    public ResponseEntity<UpdateBookingResponseDto> updateBooing(@RequestBody UpdateBookingRequestDto updateBookingRequestDto, @PathVariable Long driverId) {

        return new ResponseEntity<>(bookingService.updateBooking(updateBookingRequestDto,driverId),HttpStatus.OK);
    }
}
