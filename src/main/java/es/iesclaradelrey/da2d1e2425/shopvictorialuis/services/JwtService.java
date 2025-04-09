package es.iesclaradelrey.da2d1e2425.shopvictorialuis.services;

import es.iesclaradelrey.da2d1e2425.shopvictorialuis.entities.AppUser;

public interface JwtService {
    String generateAccessToken(AppUser appUser);

    String generateRefreshToken(AppUser appUser);

    void validateAccessToken(String token);

    void validateRefreshToken(String token);

    String extractAlias(String token);
}
