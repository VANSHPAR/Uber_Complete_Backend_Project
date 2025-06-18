package com.example.uberprojectauthservice.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PassengerDto {
    private String name;

    private String email;

    private String password; //encrypted password
}
