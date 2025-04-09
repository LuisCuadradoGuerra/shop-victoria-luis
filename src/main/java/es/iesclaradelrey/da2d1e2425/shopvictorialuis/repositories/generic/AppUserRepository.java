package es.iesclaradelrey.da2d1e2425.shopvictorialuis.repositories.generic;

import es.iesclaradelrey.da2d1e2425.shopvictorialuis.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByAppUserFirstName(String appUserFirstName);
    Optional<AppUser> findByAppUserEmail(String appUserEmail);
    Optional<AppUser> findAppUserByAppUserAlias(String appUserAlias);
    boolean existsByAppUserAlias(String appUserAlias);
}
