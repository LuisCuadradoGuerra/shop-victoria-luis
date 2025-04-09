package es.iesclaradelrey.da2d1e2425.shopvictorialuis.filters;

import es.iesclaradelrey.da2d1e2425.shopvictorialuis.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String pathRequest = request.getRequestURI();

        if (pathRequest.contains("/api/app")) {
            String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                todo return problemDetails
//                Devuelve un json de problemDetails
                return;
            }

//            Extraer el token
            String token = authHeader.substring(7);

//            Validar el token
            try {
                jwtService.validateAccessToken(token);

//                Extraer el nombre de usuario
                String alias = jwtService.extractAlias(token);

//                Fijar el usuario en el contexto de seguridad
                UserDetails userDetails = userDetailsService.loadUserByUsername(alias);
                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

        }

        filterChain.doFilter(request, response);
    }
}
