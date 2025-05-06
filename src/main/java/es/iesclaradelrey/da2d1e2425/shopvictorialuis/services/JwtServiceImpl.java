package es.iesclaradelrey.da2d1e2425.shopvictorialuis.services;

import es.iesclaradelrey.da2d1e2425.shopvictorialuis.entities.AppUser;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.utils.JwtTokenType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {

//    Valores sacados del properties
    @Value("${security.jwt.signing-key-secret}") // Firma
    private String signingKeySecret;
    @Value("${security.jwt.access-token-ttl}") // Caducidad de tokens
    private long accessTokenTtl;
    @Value("${security.jwt.refresh-token-ttl}")
    private long accessTokenRefreshTtl;

    @Override
    public String generateAccessToken(AppUser appUser) {
        SecretKey key = Keys.hmacShaKeyFor(signingKeySecret.getBytes());

        return Jwts.builder()
                .signWith(key)
                .subject(appUser.getAppUserAlias())
                .claim("type", JwtTokenType.ACCESS_TOKEN)

//                No hace falta toda esta información de usuario, pero que se sepa que podemos ponerla
                .claim("firstName", appUser.getAppUserFirstName())
                .claim("lastName", appUser.getAppUserLastName())
//                Nunca se suma al token la contraseña y si evitamos pasarle el email, mejor

                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + accessTokenTtl))
                .compact();

    }

    @Override
    public String generateRefreshToken(AppUser appUser) {
        SecretKey key = Keys.hmacShaKeyFor(signingKeySecret.getBytes());

        return Jwts.builder()
                .signWith(key)
                .subject(appUser.getAppUserAlias())
                .claim("type", JwtTokenType.REFRESH_TOKEN)

//                No hace falta toda esta información de usuario, pero que se sepa que podemos ponerla
                .claim("firstName", appUser.getAppUserFirstName())
                .claim("lastName", appUser.getAppUserLastName())
//                Nunca se suma al token la contraseña y si evitamos pasarle el email, mejor

                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + accessTokenRefreshTtl))
                .compact();
    }

    @Override
    public void validateAccessToken(String token){
        Claims claims = getTokenClaims(token);
        // Esto no funciona
        // JwtTokenType type = claims.get("type", JwtTokenType.class);
        JwtTokenType type = JwtTokenType.valueOf(claims.get("type").toString());
        if(type != JwtTokenType.ACCESS_TOKEN){
            throw new JwtException("Invalid token type");
        }
    }

    @Override
    public void validateRefreshToken(String token){
        Claims claims = getTokenClaims(token);
        // JwtTokenType type = claims.get("type", JwtTokenType.class);
        JwtTokenType type = JwtTokenType.valueOf(claims.get("type").toString());
        if(type != JwtTokenType.REFRESH_TOKEN){
            throw new JwtException("Invalid token type");
        }
    }

    @Override
    public String extractAlias(String token) {
        Claims claims = getTokenClaims(token);
        return claims.getSubject();
    }

    private Claims getTokenClaims(String token) {
        SecretKey key = Keys.hmacShaKeyFor(signingKeySecret.getBytes());
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
