package com.example.UberReviewService.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Entity
@Table(name="Booking_review")

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
public class Review extends BaseModel{


    @Column(nullable = false)
    private String content;

    private Double rating;




    @Override
    public String toString() {
        return "Review{" +

                ", content='" + content + '\'' +
                ", rating=" + rating +
                ", createAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
