package es.iesclaradelrey.da2d1e2425.shopvictorialuis.restcontrollers.app;

import es.iesclaradelrey.da2d1e2425.shopvictorialuis.dto.app.LoginUserDto;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.dto.app.RegisterUserDto;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.dto.app.TokensDto;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.entities.AppUser;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.services.AppUserService;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.services.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/app/v1/auth")
public class AuthController {

    private final AppUserService appUserService;
    private final JwtService jwtService;

    public AuthController(AppUserService appUserService, JwtService jwtService) {
        this.appUserService = appUserService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<TokensDto> register(@RequestBody RegisterUserDto registerUserDto) {
        AppUser appUser = appUserService.register(registerUserDto);
        String accessToken = jwtService.generateAccessToken(appUser);
        String refreshToken = jwtService.generateRefreshToken(appUser);
        return ResponseEntity.ok(TokensDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build());
    }

    @PostMapping("/login")
    public ResponseEntity<TokensDto> login(@RequestBody LoginUserDto loginUserDto) {
        AppUser appUser = appUserService.login(loginUserDto);
        String accessToken = jwtService.generateAccessToken(appUser);
        String refreshToken = jwtService.generateRefreshToken(appUser);
        return ResponseEntity.ok(TokensDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build());
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokensDto> refresh() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @PostMapping("/revoque")
    public ResponseEntity<Void> revoke() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
