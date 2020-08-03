package pl.zalwi.login.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class SystemUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String firstName;
    String lastName;
    String emailAddress;
    String userPasswordHash;

    @Enumerated(EnumType.STRING)
    UserType userType;

    String token;

    public String basicInfo(){
        return firstName + " " + lastName + " (" + userType.getDescription() + ")";
    }
}
