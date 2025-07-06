package com.example.clientsocketservice.controller;

import com.example.clientsocketservice.dto.RideRequestDto;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/socket")
public class DriverRequestController {
    private final SimpMessagingTemplate simpMessagingTemplate;



    public DriverRequestController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @GetMapping("/newride")
    public void raiseRideRequest(RideRequestDto rideRequestDto) {
        sendDriversNewRideRequest(rideRequestDto);
    }
    public void sendDriversNewRideRequest(@RequestBody  RideRequestDto  rideRequestDto) {
        System.out.println("Executed periodic function");
        simpMessagingTemplate.convertAndSend("/topic/rideRequest",rideRequestDto);
        System.out.println("Request complete");
        return;
    }
}
