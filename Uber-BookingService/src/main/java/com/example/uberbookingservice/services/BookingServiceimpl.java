package com.example.uberbookingservice.services;

import com.example.uberbookingservice.Repositories.BookingRepository;
import com.example.uberbookingservice.Repositories.PassengerRepository;
import com.example.uberbookingservice.dto.CreateBooikngDto;
import com.example.uberbookingservice.dto.CreateBookingResponseDto;
import com.example.uberbookingservice.dto.DriverLocationDto;
import com.example.uberbookingservice.dto.NearbyDriverRequestDto;
import com.example.uberproject_entityservice.models.Booking;
import com.example.uberproject_entityservice.models.BookingStatus;
import com.example.uberproject_entityservice.models.Passenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceimpl implements BookingService{


    private final PassengerRepository passengerRepository;
    private final BookingRepository bookingRepository;


    private  final RestTemplate restTemplate;

    private static  final String LOCATION_SERVICE="http://localhost:8080";

    public BookingServiceimpl(PassengerRepository passengerRepository, BookingRepository bookingRepository) {
        this.passengerRepository = passengerRepository;
        this.bookingRepository = bookingRepository;

        this.restTemplate = new RestTemplate();
    }

    @Override
    public CreateBookingResponseDto createBooking(CreateBooikngDto createBooikngDto) {
        Optional<Passenger> passenger=passengerRepository.findById(createBooikngDto.getPassengerId());
        Booking  booking = Booking.builder()
                .bookingStatus(BookingStatus.ASSIGNING_DRIVER)
                .startLocation(createBooikngDto.getStartLocation())
                .endLocation(createBooikngDto.getEndLocation())
                .passenger(passenger.get())
                .build();
        Booking newBooking=bookingRepository.save(booking);

        //make an api call to location service to fetch nearby drivers
        NearbyDriverRequestDto request= NearbyDriverRequestDto.builder()
                .Latitude(createBooikngDto.getStartLocation().getLatitude())
                .Longitude(createBooikngDto.getStartLocation().getLongitude())
                .build();


        ResponseEntity<DriverLocationDto[]> result = restTemplate.postForEntity(LOCATION_SERVICE+"/api/location/nearby/drivers",request,DriverLocationDto[].class);

        if(result.getStatusCode().is2xxSuccessful() && result.getBody().length>0){
            List<DriverLocationDto> driverLocations= Arrays.asList(result.getBody());
            driverLocations.forEach(driverLocation->{
                System.out.println(driverLocation.getDriverId()+" "+driverLocation.getLatitude()+" "+driverLocation.getLongitude());
            });
        }

        return CreateBookingResponseDto.builder()
                .bookingId(newBooking.getId())
                .bookingStatus(newBooking.getBookingStatus().toString())
               .build();
    }
}
