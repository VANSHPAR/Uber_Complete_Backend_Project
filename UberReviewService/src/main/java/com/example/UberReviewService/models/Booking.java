package com.example.UberReviewService.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Booking extends BaseModel{






    @Enumerated(value= EnumType.STRING) //to specify  BookingStatus is enum we use @Enumerated and it gives facility->if you want to store this enum as number or as string
    private BookingStatus bookingStatus;

    @Temporal(value= TemporalType.TIMESTAMP)
    private Date startTime;

    @Temporal(value= TemporalType.TIMESTAMP)
    private Date endTime;

    private Long totalDistance;

    //Booking has one Driver
    @ManyToOne
    private Driver driver;

    @ManyToOne
    private Passenger passenger;


}
