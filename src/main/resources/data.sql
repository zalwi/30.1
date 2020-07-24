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
    ('1','kamil@test.com','Kamil','Zalwert','600 123 123');

INSERT INTO
    SERVICE_ORDER (ID, CAR_MODEL, CAR_PRODUCER, END_DATE, ENGINE_CAPACITY, FIRST_DATE_REGISTRATION, FUEL_TYPE, ORDER_DATA_TIME, REGISTRATION_NUMBER, VIN_NUMBER, CONTACT_PERSON_ID)
VALUES
    ('1','100','Audi','2020-07-24 15:00', '2600','1994-08-12','LPG','2020-07-24 8:00', 'EWI 1234', '1234567890', '1');

INSERT INTO
    ORDER_TASK (ID, COST, DESCRIPTION, TASK_END_DATE, TASK_START_DATE, TASK_STATUS, SERVICE_ORDER_ID, SYSTEM_USER_ID)
VALUES
    ('1','100','Diagnostyka', null,'2020-07-24 8:30', 'NEW', '1', '5');