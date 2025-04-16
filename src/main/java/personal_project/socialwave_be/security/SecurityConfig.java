package personal_project.socialwave_be.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    //GlobalCorsConfig - CORS configuration
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(List.of("http://localhost:5173")); //frontend origin
        config.setAllowedMethods(List.of("GET", "PUT", "DELETE", "PATCH", "POST")); //HTTP methods allowed
        config.setAllowedHeaders(List.of("*")); //allow backend to accept any custom headers sent by the frontend
        config.setAllowCredentials(true); //allow cookies, Authorization headers to be sent along with the request

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); //apply this CORS policy to all endpoints
        return source;
    }

    @Bean
    //SecurityFilterChain - handle authentication tasks
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) //disabled this, no need for RESTful API project
                //enable cors - this needs GlobalCorsConfig
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(
                                "/api/users/signup",
                                "/api/users/login",
                                "/api/users/track"
                        ).permitAll() //only permit by default the mentioned APIs
                        .anyRequest().authenticated() //require authentication for other API calls
                );

        return http.build();
    }

}
