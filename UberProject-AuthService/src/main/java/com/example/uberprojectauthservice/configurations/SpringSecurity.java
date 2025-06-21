package com.example.uberprojectauthservice.configurations;

import com.example.uberprojectauthservice.services.UserDetailsServiceimpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurity  {

    @Bean
   public UserDetailsService userDetailsService(){
       return new UserDetailsServiceimpl();
   }
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
   // AuthenticationProvider provides different authentication schemes to be plugable in our application
   //AuthenticationProvider processes an authentication request and return authenticated obj..
   @Bean
   public AuthenticationProvider authenticationProvider(){
       DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
       daoAuthenticationProvider.setUserDetailsService(userDetailsService());
       daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
       return daoAuthenticationProvider;

   }

   @Bean
   public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
   }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
