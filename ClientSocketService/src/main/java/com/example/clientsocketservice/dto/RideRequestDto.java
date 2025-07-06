package com.example.clientsocketservice.dto;

import com.example.clientsocketservice.models.ExactLocation;

import java.util.List;

public class RideRequestDto {

    private Long passengerId;

    private ExactLocation startLocation;

    private ExactLocation endLocation;

    private List<Long> driverIds;
}
