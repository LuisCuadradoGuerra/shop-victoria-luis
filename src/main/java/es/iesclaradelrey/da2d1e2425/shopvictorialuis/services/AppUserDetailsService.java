package es.iesclaradelrey.da2d1e2425.shopvictorialuis.services;

import es.iesclaradelrey.da2d1e2425.shopvictorialuis.entities.AppUser;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.repositories.generic.AppUserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsService implements UserDetailsService {
    private final AppUserRepository appUserRepository;

    public AppUserDetailsService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String alias) throws UsernameNotFoundException {

        AppUser appUser = appUserRepository
                .findAppUserByAppUserAlias(alias)
                .orElseThrow(
                        () -> new UsernameNotFoundException(String.format("User '%s' not found", alias))
                );

/*
Cosas que se pueden devolver en el user details
        UserDetails userDetails = null;
        userDetails.isAccountNonExpired();
        userDetails.isCredentialsNonExpired();
        userDetails.isEnabled();
        userDetails.isAccountNonLocked();
*/


//        Dos maneras de hacer lo mismo

//        return User.builder()
//                .username(alias)
//                .password(appUser.getAppUserPassword())
//                .roles("USER") ***
//                .build();

        return User.withUsername(alias)
                .password(appUser.getAppUserPassword())
                .build();
    }
}
