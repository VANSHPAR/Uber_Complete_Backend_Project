ALTER TABLE passenger
ADD COLUMN phone_number VARCHAR(255) NOT NULL;


ALTER TABLE passenger
    ADD COLUMN email VARCHAR(255) NOT NULL;

ALTER TABLE passenger
    ADD COLUMN password VARCHAR(255) NOT NULL;

ALTER TABLE passenger
    MODIFY name  VARCHAR(255) NOT NULL;
