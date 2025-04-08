package es.iesclaradelrey.da2d1e2425.shopvictorialuis.dto.app;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RegisterUserDto {
    private String appUserAlias;
    private String appUserFirstName;
    private String appUserLastName;
    private String appUserEmail;
    private String appUserPassword;
}
