package pl.zalwi.global.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.zalwi.login.model.SystemUser;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class OrderTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Enumerated(EnumType.STRING)
    TaskStatus taskStatus;

    LocalDateTime taskStartDate;
    LocalDateTime taskEndDate;
    String description;
    BigDecimal cost;

    @OneToOne
    SystemUser systemUser;

    @ManyToOne
    ServiceOrder serviceOrder;
}
