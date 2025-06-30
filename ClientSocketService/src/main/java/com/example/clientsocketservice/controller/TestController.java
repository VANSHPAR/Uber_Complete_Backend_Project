package com.example.clientsocketservice.controller;

import com.example.clientsocketservice.dto.TestResponse;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;


@Controller
public class TestController {

    @MessageMapping("/ping")
    @SendTo("/topic/ping")
    public TestResponse pingCheck(TestResponse message) {
        System.out.println("recieved message from client "+ message.getData());
        TestResponse response = TestResponse.builder().data("Recieved").build();
        return response;
    }

    @SendTo("/topic/scheduled")
    @Scheduled(fixedRate = 2000)
    public String sendPeriodicMessage() {
        return "Periodic message from server "+System.currentTimeMillis();
    }


}
