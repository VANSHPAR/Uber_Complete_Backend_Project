package com.example.uberprojectauthservice.controllers;

import com.example.uberprojectauthservice.dto.AuthRequestDto;
import com.example.uberprojectauthservice.dto.AuthResponseDto;
import com.example.uberprojectauthservice.dto.PassengerDto;
import com.example.uberprojectauthservice.dto.PassengerSignuprequestDto;
import com.example.uberprojectauthservice.services.AuthService;
import com.example.uberprojectauthservice.services.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Value("${cookie.expiry}")
    private int cookieExpiry;

    private AuthService authService;

    private AuthenticationManager authenticationManager;

    private JwtService  jwtService;


    public AuthController(AuthService authService,AuthenticationManager authenticationManager,JwtService jwtService) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/signup/passenger")
    public ResponseEntity<PassengerDto> signup(@RequestBody PassengerSignuprequestDto passengerSignuprequestDto){
        PassengerDto response=authService.signupPassenger(passengerSignuprequestDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/signin/passenger")
    public ResponseEntity<?> signIn(@RequestBody AuthRequestDto authRequestDto  , HttpServletResponse response){
        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDto.getEmail(),authRequestDto.getPassword()));
        //UsernamePasswordAuthenticationToken comes from spring
        if(authentication.isAuthenticated()){
            String jwtToken=jwtService.createToken(authRequestDto.getEmail());
            ResponseCookie cookie=ResponseCookie.from("jwtToken",jwtToken)
                    .httpOnly(true)//if we make it true client can't access jwt token in his browser ,if it is false client can access it.
                    .secure(false)
                    .path("/")
                    .maxAge(7*24*3600)
                    .build();

            response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());

            return new ResponseEntity<>(AuthResponseDto.builder().success(true).build(), HttpStatus.OK);
        }
        else throw new UsernameNotFoundException("Invalid email or password");
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validate(HttpServletRequest request){

        for(Cookie cookie:request.getCookies()){
            System.out.println(cookie.getName()+":"+cookie.getValue());
        }
        return new  ResponseEntity<>("Success",HttpStatus.OK);
    }
}
