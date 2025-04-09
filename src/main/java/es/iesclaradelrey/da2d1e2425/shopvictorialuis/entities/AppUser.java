package es.iesclaradelrey.da2d1e2425.shopvictorialuis.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "app_users")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "app_user_id", nullable = false)
    private Long appUserId;

    @Size(max = 50)
    @NotNull
    @Column(name = "app_user_alias", nullable = false, length = 50)
    private String appUserAlias;

    @Size(max = 50)
    @NotNull
    @Column(name = "app_user_first_name", nullable = false, length = 50)
    private String appUserFirstName;

    @Size(max = 50)
    @NotNull
    @Column(name = "app_user_last_name", nullable = false, length = 50)
    private String appUserLastName;

    @Size(max = 200)
    @NotNull
    @Column(name = "app_user_password", nullable = false, length = 200)
    private String appUserPassword;

    @Size(max = 200)
    @NotNull
    @Column(name = "app_user_email", nullable = false, length = 200)
    private String appUserEmail;

}