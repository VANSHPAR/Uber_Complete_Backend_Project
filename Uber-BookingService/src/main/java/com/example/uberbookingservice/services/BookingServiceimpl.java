package com.example.uberbookingservice.services;

import com.example.uberbookingservice.Repositories.BookingRepository;
import com.example.uberbookingservice.Repositories.DriverRepository;
import com.example.uberbookingservice.Repositories.PassengerRepository;
import com.example.uberbookingservice.apis.LocationServiceApi;
import com.example.uberbookingservice.dto.*;
import com.example.uberproject_entityservice.models.Booking;
import com.example.uberproject_entityservice.models.BookingStatus;
import com.example.uberproject_entityservice.models.Driver;
import com.example.uberproject_entityservice.models.Passenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceimpl implements BookingService{


    private final PassengerRepository passengerRepository;
    private final BookingRepository bookingRepository;


    private  final RestTemplate restTemplate;

    private final LocationServiceApi locationServiceApi;
    private final DriverRepository driverRepository;


    //  private static  final String LOCATION_SERVICE="http://localhost:8080";

    public BookingServiceimpl(PassengerRepository passengerRepository, BookingRepository bookingRepository, LocationServiceApi locationServiceApi, DriverRepository driverRepository) {
        this.passengerRepository = passengerRepository;
        this.bookingRepository = bookingRepository;
        this.locationServiceApi = locationServiceApi;

        this.restTemplate = new RestTemplate();
        this.driverRepository = driverRepository;
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

        processNearByDriversAsync(request);

//
//        ResponseEntity<DriverLocationDto[]> result = restTemplate.postForEntity(LOCATION_SERVICE+"/api/location/nearby/drivers",request,DriverLocationDto[].class);
//
//        if(result.getStatusCode().is2xxSuccessful() && result.getBody().length>0){
//            List<DriverLocationDto> driverLocations= Arrays.asList(result.getBody());
//            driverLocations.forEach(driverLocation->{
//                System.out.println(driverLocation.getDriverId()+" "+driverLocation.getLatitude()+" "+driverLocation.getLongitude());
//            });
//        }

        return CreateBookingResponseDto.builder()
                .bookingId(newBooking.getId())
                .bookingStatus(newBooking.getBookingStatus().toString())
               .build();
    }

    @Override
    public UpdateBookingResponseDto updateBooking(UpdateBookingRequestDto updateBookingRequestDto, Long bookingId) {

            Optional<Driver> driver=driverRepository.findById(updateBookingRequestDto.getDriverId().get());
            bookingRepository.updateBookingStatusAndDriverById(bookingId,updateBookingRequestDto.getBookingStatus(),driver.get());

            Booking booking=bookingRepository.findById(bookingId).get();
            return UpdateBookingResponseDto.builder()
            .bookingId(bookingId)
                    .bookingStatus(booking.getBookingStatus())
                    .driver(Optional.ofNullable(booking.getDriver()))
                    .build();


    }

    private void processNearByDriversAsync(NearbyDriverRequestDto requestDto) {
        Call<DriverLocationDto[]> call=locationServiceApi.getNearbyDrivers(requestDto);
        call.enqueue(new  Callback<DriverLocationDto[]>() {
            @Override
            public void onResponse(Call<DriverLocationDto[]> call, Response<DriverLocationDto[]> response) {
            if(response.isSuccessful() && response.body()!=null){
            List<DriverLocationDto> driverLocations= Arrays.asList(response.body());
            driverLocations.forEach(driverLocation->{
                System.out.println(driverLocation.getDriverId()+" "+driverLocation.getLatitude()+" "+driverLocation.getLongitude());
            });
        }
            else System.out.println("Request failed"+response.message());
            }

            @Override
            public void onFailure(Call<DriverLocationDto[]> call, Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }


}
