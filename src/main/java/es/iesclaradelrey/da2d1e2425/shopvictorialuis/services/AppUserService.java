package es.iesclaradelrey.da2d1e2425.shopvictorialuis.services;

import es.iesclaradelrey.da2d1e2425.shopvictorialuis.dto.app.LoginUserDto;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.dto.app.RegisterUserDto;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.entities.AppUser;

public interface AppUserService {
    AppUser register(RegisterUserDto registerUserDto);

    AppUser login(LoginUserDto loginUserDto);

    Long getCurrentAppUserId();

    AppUser getCurrentAppUser();
}
