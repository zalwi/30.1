INSERT INTO
    system_user (id, first_name, last_name, email_address_hash, user_password_hash, user_type, token)
VALUES
    (1, 'Kamil', 'Zalwert', '4yi12y3', '31asdgayd7', 'CHIEF', '1u4yi12y3i31asdgayd786'),
    (2, 'Jan', 'Pierwszy', '4yi12y3', '31asdgayd7', 'ELECTRICIAN', '1u4yi12y3i31asdgayd786'),
    (3, 'Paweł', 'Drugi', '4yi12y3', '31asdgayd7', 'MECHANIC', '1u4yi12y3i31asdgayd786'),
    (4, 'Mateusz', 'Parówka', '4yi12y3', '31asdgayd7#', 'MECHANIC', '1u4yi12y3i31asdgayd786'),
    (5, 'Dominik', 'Kowalski', '4yi12y3', '31asdgayd7#', 'DIAGNOSTICIAN', '1u4yi12y3i31asdgayd786'),
    (6, 'Krzysztof', 'Nowak', '4yi12y3', '31asdgayd7', 'TRAINEE', '1u4yi12y3i31asdgayd786');

INSERT INTO
    CONTACT_PERSON (ID, EMAIL_ADDRESS, FIRST_NAME, LAST_NAME, PHONE_NUMBER)
VALUES
    ('1','kamil@test.com','Kamil','Zalwert','600 123 123'),
    ('2','maciej@test.com','Maciej','Kowalski','631 321 312'),
    ('3','piotr@test.com','Piotr','Pierwszy','808 987 654');

INSERT INTO
    SERVICE_ORDER (ID, MODEL, PRODUCER, ENGINE_CAPACITY, FIRST_DATE_REGISTRATION, FUEL_TYPE, CREATION_DATE, DEADLINE_DATE, REGISTRATION_NUMBER, VIN_NUMBER, CONTACT_PERSON_ID, FINISHED)
VALUES
    ('1','100','Audi', '2600','1994-08-12','LPG','2020-07-24 8:00','2020-07-24 15:00', 'EWI 1234', '1234567890', '1', false),
    ('2','Golf 4 Lift','VW', '1600','2012-06-11','DIESEL','2020-07-24 8:00','2020-07-31 18:00', 'DW 32814', '1234567890', '2', false),
    ('3','Passat','VW', '2000','2012-06-11','GASOLINE','2020-07-24 8:00','2020-08-10 13:00', 'DWR 9583', '1234567890', '3', false);

INSERT INTO
    SERVICE_ORDER (ID, MODEL, PRODUCER, ENGINE_CAPACITY, FIRST_DATE_REGISTRATION, FUEL_TYPE, CREATION_DATE, DEADLINE_DATE, END_DATE, REGISTRATION_NUMBER, VIN_NUMBER, CONTACT_PERSON_ID, FINISHED)
VALUES
    ('4','Garbus','VW', '1300','1975-01-18','GASOLINE','2020-06-24 9:00','2020-08-10 13:00', '2020-07-31 10:00', 'DW 1234', '1234567890', '3', true);

INSERT INTO
    ORDER_TASK (ID, COST, DESCRIPTION, TASK_END_DATE, TASK_START_DATE, TASK_STATUS, SERVICE_ORDER_ID, SYSTEM_USER_ID)
VALUES
    ('1','100','Diagnostyka', null,'2020-07-24 8:30', 'NEW', '1', '5');