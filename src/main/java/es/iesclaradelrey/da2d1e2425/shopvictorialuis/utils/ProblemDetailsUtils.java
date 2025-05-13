package es.iesclaradelrey.da2d1e2425.shopvictorialuis.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;

public class ProblemDetailsUtils {
    public static void writeUnauthorized(@NonNull HttpServletResponse response, HttpServletRequest request, String errorMessage) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

    }
}
