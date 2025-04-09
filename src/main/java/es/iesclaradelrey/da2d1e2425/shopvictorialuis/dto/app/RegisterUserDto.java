package es.iesclaradelrey.da2d1e2425.shopvictorialuis.dto.app;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RegisterUserDto {
    @NotBlank(message = "It's hard but necessary an alias")
    private String appUserAlias;
    @NotBlank(message = "Don't you have a name or what?")
    private String appUserFirstName;
    @NotBlank(message = "If you don't have one, write X")
    private String appUserLastName;
    @NotBlank(message = "Is not for spam my dear")
    private String appUserEmail;
    @NotBlank(message = "You need a password for your profile")
    @Size(min = 6, max = 70, message = "The password must have size between 6 and 70")
    private String appUserPassword;
}
