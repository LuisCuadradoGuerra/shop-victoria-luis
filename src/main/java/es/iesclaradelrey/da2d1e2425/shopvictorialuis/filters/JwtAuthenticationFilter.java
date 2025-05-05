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
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    // Lista de rutas públicas
    private static final List<String> PUBLIC_PATHS = List.of(
            "/api/app/v1/auth",
            "/api/app/v1/products/find"
    );

    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String pathRequest = request.getRequestURI();

        // Ignorar validación si la ruta está en la lista blanca
        if (isPublicPath(pathRequest)) {
            filterChain.doFilter(request, response);
            return;
        }

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
//                Cambios por GPT: userDetails.getAuthorities()
                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

        }

        filterChain.doFilter(request, response);
    }

    private boolean isPublicPath(String path) {
        return PUBLIC_PATHS.stream().anyMatch(path::startsWith);
    }
}
