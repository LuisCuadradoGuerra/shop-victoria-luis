package es.iesclaradelrey.da2d1e2425.shopvictorialuis.services;

import es.iesclaradelrey.da2d1e2425.shopvictorialuis.dto.app.LoginUserDto;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.dto.app.RegisterUserDto;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.entities.AppUser;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.exceptions.UserAliasAlreadyExistException;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.repositories.generic.AppUserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AppUserServiceImpl implements AppUserService {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AppUserServiceImpl(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public AppUser register(RegisterUserDto registerUserDto) {
        if (appUserRepository.existsByAppUserAlias(registerUserDto.getAppUserAlias())) {
            throw new UserAliasAlreadyExistException(registerUserDto.getAppUserAlias());
        }

        AppUser appUser = AppUser.builder()
                .appUserAlias(registerUserDto.getAppUserAlias())
                .appUserFirstName(registerUserDto.getAppUserFirstName())
                .appUserLastName(registerUserDto.getAppUserLastName())
                .appUserEmail(registerUserDto.getAppUserEmail())
                .appUserPassword(passwordEncoder.encode(registerUserDto.getAppUserPassword()))
                .build();

        return appUserRepository.save(appUser);
    }

    @Override
    public AppUser login(LoginUserDto loginUserDto) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginUserDto.getAppUserAlias(), loginUserDto.getAppUserPassword());
        authenticationManager.authenticate(authenticationToken);
        return appUserRepository.findAppUserByAppUserAlias(loginUserDto.getAppUserAlias())
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Alias: %s is in use", loginUserDto.getAppUserAlias())));
    }

    @Override
    public Long getCurrentAppUserId() {
        return this.getCurrentAppUser().getAppUserId();
    }

    @Override
    public AppUser getCurrentAppUser() {
        // Obtener cual es el usuario logado
        String alias = SecurityContextHolder.getContext().getAuthentication().getName();
        // buscar el usuario en el repositorio
        return appUserRepository.findAppUserByAppUserAlias(alias).orElseThrow(() ->
                new UsernameNotFoundException(String.format("User %s not found", alias)));
    }
}
