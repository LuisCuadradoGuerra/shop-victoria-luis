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
    @Column(name = "appUser_id", nullable = false)
    private Long appUserId;

    @Size(max = 50)
    @NotNull
    @Column(name = "appUserAlias", nullable = false, length = 50)
    private String appUserAlias;

    @Size(max = 50)
    @NotNull
    @Column(name = "appUserFirstName", nullable = false, length = 50)
    private String appUserFirstName;

    @Size(max = 50)
    @NotNull
    @Column(name = "appUserLastName", nullable = false, length = 50)
    private String appUserLastName;

    @Size(max = 200)
    @NotNull
    @Column(name = "appUserPassword", nullable = false, length = 200)
    private String appUserPassword;

    @Size(max = 200)
    @NotNull
    @Column(name = "appUserEmail", nullable = false, length = 200)
    private String appUserEmail;

}