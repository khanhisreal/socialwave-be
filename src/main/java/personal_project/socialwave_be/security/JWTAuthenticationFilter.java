package personal_project.socialwave_be.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTGenerator tokenJwtGenerator;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    /**
     * Use:
     * this middleware checks before the request reaches the controller to see if it
     * contains token in its header or not
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();

        //skip authentication for /auth/** routes
        //skip authentication for /uploads
        if (request.getRequestURI().startsWith("/api/auth/") || path.startsWith("/uploads/")) {
            filterChain.doFilter(request, response); //allow the request
            return;
        }

        String token = getJWTFromRequest(request);

        if (StringUtils.hasText(token) && tokenJwtGenerator.validateToken(token)) {
            String username = tokenJwtGenerator.getUsernameFromJWT(token);

            // Load the user without roles (since every user is equal)
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

            // Create authentication with an empty authorities list
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
            );

            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // Set the authentication in the context
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired JWT token");
            return;  // Stop the filter chain if the JWT is invalid
        }
        filterChain.doFilter(request, response);
    }


    private String getJWTFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

}
