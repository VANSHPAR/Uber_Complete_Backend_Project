package com.example.uberprojectauthservice.helper;

import com.example.uberprojectauthservice.models.Passenger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
//in spring security,spring security does authentication on userdetails object hence this
//class convert our passenger obj. into polymorphic type of userdetails obj. .
public class AuthPassengerDetails extends Passenger implements UserDetails {

    private String username;

    private String password;

    public AuthPassengerDetails(Passenger passenger){
        this.username=passenger.getEmail();
        this.password=passenger.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
