package pl.zalwi.global.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ServiceOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String carProducer;
    String carModel;
    String registrationNumber;
    String vinNumber;
    LocalDate firstDateRegistration;
    Integer engineCapacity;

    @Enumerated(EnumType.STRING)
    FuelType fuelType;

    LocalDateTime orderDataTime;
    LocalDateTime endDate;

    @OneToOne
    ContactPerson contactPerson;

    @OneToMany(mappedBy = "serviceOrder")
    List<OrderTask> orderTaskList;
}
