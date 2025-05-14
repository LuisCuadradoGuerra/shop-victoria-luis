package es.iesclaradelrey.da2d1e2425.shopvictorialuis.filters;

import es.iesclaradelrey.da2d1e2425.shopvictorialuis.services.JwtService;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.utils.ProblemDetailsUtils;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    // Nombre de la cabecera de autorización
    private static final String AUTH_HEADER = "Authorization";
    // Prefijo que precede al token JWT
    private static final String BEARER_PREFIX = "Bearer ";
    // Ruta que se desea proteger
    private static final String PROTECTED_PATH = "/api/**";

    private static final String AUTH_PATH = "/api/app/v1/auth/**";

//    private static final String INDEX_PATH = "/";
    // Matcher para comprobar si una petición está en la ruta protegida
    private static final AntPathRequestMatcher protectedPathMatcher = new AntPathRequestMatcher(PROTECTED_PATH);

    private static final AntPathRequestMatcher authPathMatcher = new AntPathRequestMatcher(AUTH_PATH);

//    private static final AntPathRequestMatcher indexPathMatcher = new AntPathRequestMatcher(INDEX_PATH);

    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }


    // El mensaje "Not annotated parameter overrides @NonNullApi parameter" que aparecer porque el paquete
    // de Spring donde se define doFilterInternal () está marcado como @NonNullApi. El paquete completo. Esto
    // significa que todos los parámetros y retornos de todos los métodos son no nulos por defecto, salvo que
    // se indique lo contrario. Esta anotación @NonNullApi está deprecada. Según el propio código de Spring,
    // Ahora hay que usar otras.
    // https://github.com/spring-projects/spring-framework/blob/main/spring-core/src/main/java/org/springframework/lang/NonNullApi.java
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        System.out.println("Analizando URL " + request.getRequestURI());

        // Primero, obtener la ruta de la petición
        // Esto no es lo más adecuado porque getRequestURI da la dirección con el contextPath.
        // Así es difícil realizar comprobaciones de rutas, porque dependen del contexpath, o de dónde se
        // desplieguen en un servidor de aplicaciones (Tomcat / GlassFish / Jetty / WebLogic / etc.)
        // String pathRequest = request.getRequestURI();

        // Mejor usar un "Matcher", que permite usar paths más flexibles.
        // En concreto, "AntPathRequestMatcher" permite trabajar directamente con la petición (HttpServletrequest)
        // ahorrando el procesamiento de cadenas. Además, permite usar comodines estilo "Apache Ant", como **, * o *.extensión
        if (protectedPathMatcher.matches(request) && !authPathMatcher.matches(request)) {
            System.out.println("Autenticando petición");
            // En el try se hacen varias cosas que pueden lanzar excepción.
            // Si cualquiera de ellas falla, se deniega el acceso.
            try {
                // Extraer cabecera de autorización
                String authHeader = request.getHeader(AUTH_HEADER);

                // Si no hay cabecera de autorización, o si no empieza con "Bearer", no se puede acceder
                if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
                    throw new JwtException("Authorization header missing or incorrect.");
                }

                // Extraer el token, eliminando "Bearer "
                String token = authHeader.substring(7);

                // Validar el token (lanza excepción si hay algún problema).
                // Específicamente, el filtro exige token de acceso, no vale uno de refresco.
                jwtService.validateAccessToken(token);

                // Una vez validado, se extrae el nombre de usuario del token
                String username = jwtService.extractAlias(token);

                // Hay que establecer en el contexto de seguridad quién es el usuario conectado. Para esto:
                // - Se obtienen los detalles del usuario con el servicio "userDetailsService"
                // - Se crea un objeto de tipo authentication. En este caso, UsernamePasswordAuthenticationToken.
                //   Se usa null para credenciales (contraseña) porque confiamos en el token JWT recibido.
                // - Se usa este objeto para fijar el usuario en el contexto de seguridad.
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                // Importante: aunque no haya roles en la aplicación, hay que pasar el tercer parámetro del constructor,
                // con los roles del usuario (aunque estén vacíos). Si no, se devolverá un 403 aunque el token sea válido.
                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                // Generar un "ProblemDetails" para la respuesta.
                String errorMessage = String.format("Error validating access token: %s", e.getMessage());
                ProblemDetailsUtils.writeUnauthorized(response, request, errorMessage);
                // Se "corta" la cadena de filtros, al no llamar a filterChain.doFilter
                return;
            }
        }

        // Se puede llegar aquí porque ha ido bien, o porque no aplicaba esta autenticación.
        // En cualquiera de los dos casos, se "pasa la pelota" al siguiente filtro.
        filterChain.doFilter(request, response);
    }
}





//
//
//
//
//
//
//
//@Component
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//    private final JwtService jwtService;
//    private final UserDetailsService userDetailsService;
//
//    // Lista de rutas públicas
//    private static final List<String> PUBLIC_PATHS = List.of(
//            "/api/app/v1/auth"
//    );
//
//    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
//        this.jwtService = jwtService;
//        this.userDetailsService = userDetailsService;
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain filterChain) throws ServletException, IOException {
//        String pathRequest = request.getRequestURI();
//
//        // Ignorar validación si la ruta está en la lista blanca
//        if (isPublicPath(pathRequest)) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        if (pathRequest.contains("/api/app")) {
//            String authHeader = request.getHeader("Authorization");
//            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
////                todo return problemDetails
////                Devuelve un json de problemDetails
//                return;
//            }
//
////            Extraer el token
//            String token = authHeader.substring(7);
//
////            Validar el token
//            try {
//                jwtService.validateAccessToken(token);
//
////                Extraer el nombre de usuario
//                String alias = jwtService.extractAlias(token);
//
////                Fijar el usuario en el contexto de seguridad
//                UserDetails userDetails = userDetailsService.loadUserByUsername(alias);
////                Cambios por GPT: userDetails.getAuthorities()
//                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//
//            } catch (Exception e) {
//                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                return;
//            }
//
//        }
//
//        filterChain.doFilter(request, response);
//    }
//
//    private boolean isPublicPath(String path) {
//        return PUBLIC_PATHS.stream().anyMatch(path::contains);
//    }
//}
