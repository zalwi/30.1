package pl.zalwi.login.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserCredentials {
    String emailAddress;
    String userPassword;
}
