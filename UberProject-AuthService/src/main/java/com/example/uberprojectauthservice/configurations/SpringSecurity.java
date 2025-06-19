package com.example.uberprojectauthservice.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurity  {

   @Bean
   public SecurityFilterChain filtercriteria(HttpSecurity http) throws Exception{
      http.csrf(
              csrf -> csrf.disable()
      );
       return http.authorizeHttpRequests(
               auth -> auth
                       .requestMatchers("/api/v1/auth/signup/*").permitAll()
                       .requestMatchers("/api/v1/auth/signin/*").permitAll()

       ).build();
   }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
