ALTER TABLE booking
DROP
FOREIGN KEY FK_BOOKING_ON_REVIEW;

ALTER TABLE booking_review
    ADD booking_id BIGINT NULL;

ALTER TABLE booking_review
    ADD CONSTRAINT FK_BOOKING_REVIEW_ON_BOOKING FOREIGN KEY (booking_id) REFERENCES booking (id);

ALTER TABLE booking
DROP
COLUMN review_id;

