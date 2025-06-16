package com.example.UberReviewService.Repositories;


import com.example.UberReviewService.models.Driver;
import com.example.UberReviewService.models.Passenger;
import com.example.UberReviewService.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository //this annotation automatically create review obj. we dont need to create
public interface ReviewRepository  extends JpaRepository<Review,Long> {
       Integer countAllByRatingIsLessThanEqual(Double rating);

       List<Review> findAllByRatingIsLessThanEqual(double rating);

       List<Review> findAllByCreatedAtBefore(Date date);

    @Query("select r from Booking b inner join Review r  where b.id= :id")
    Review findReviewByBookingId(Long id);


}


