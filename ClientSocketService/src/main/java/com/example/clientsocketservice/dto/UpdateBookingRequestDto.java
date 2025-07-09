package com.example.clientsocketservice.dto;

import com.example.uberproject_entityservice.models.BookingStatus;
import lombok.*;

import java.util.Optional;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBookingRequestDto {

    private String bookingStatus;
    private Optional<Long> driverId;
}
