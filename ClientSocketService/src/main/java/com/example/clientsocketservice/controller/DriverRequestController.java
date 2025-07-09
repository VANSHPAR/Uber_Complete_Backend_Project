package com.example.clientsocketservice.controller;

import com.example.clientsocketservice.dto.RideRequestDto;
import com.example.clientsocketservice.dto.RideResposeDto;
import com.example.clientsocketservice.dto.UpdateBookingRequestDto;
import com.example.clientsocketservice.producers.KafkaProducerService;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Controller
@RequestMapping("/api/socket")
public class DriverRequestController {
    private final SimpMessagingTemplate simpMessagingTemplate;

    private final RestTemplate restTemplate;

    private final KafkaProducerService kafkaProducerService;



    public DriverRequestController(SimpMessagingTemplate simpMessagingTemplate, KafkaProducerService kafkaProducerService) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.kafkaProducerService = kafkaProducerService;
        this.restTemplate = new RestTemplate();
    }

    @PostMapping("/newride")
    @CrossOrigin(originPatterns = "*")
    public ResponseEntity<Boolean> raiseRideRequest(@RequestBody RideRequestDto rideRequestDto) {
        System.out.println("Request for ride recieved");
        sendDriversNewRideRequest(rideRequestDto);
        System.out.println("Request complete");
        return new ResponseEntity<>(Boolean.TRUE,HttpStatus.OK);
    }


    public void sendDriversNewRideRequest(  RideRequestDto  rideRequestDto) {
       // System.out.println("Executed periodic function");
        simpMessagingTemplate.convertAndSend("/topic/rideRequest",rideRequestDto);


    }

    @GetMapping
    public Boolean help(){
        kafkaProducerService.publishMessage("sample-topic","Hello World");
        return Boolean.TRUE;
    }


    @MessageMapping("/rideResponse/{userId}")
    public synchronized  void rideResponseHandler(@DestinationVariable String userId, RideResposeDto rideResposeDto) {

        System.out.println(rideResposeDto.getResponse()+" "+userId);
        UpdateBookingRequestDto updateBookingRequestDto=UpdateBookingRequestDto.builder()
                .driverId(Optional.of(Long.parseLong(userId)))
                .bookingStatus("SCHEDULED")
                .build();
        ResponseEntity<UpdateBookingRequestDto> result=this.restTemplate.postForEntity("http://localhost:7464/api/v1/booking/"+rideResposeDto.bookingId,updateBookingRequestDto, UpdateBookingRequestDto.class);
        System.out.println(result.getStatusCode());
    }

}
