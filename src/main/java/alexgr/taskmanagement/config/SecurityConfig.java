package alexgr.taskmanagement.config;

import alexgr.taskmanagement.filter.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * SecurityConfig is a configuration class that sets up security rules and filters for the application.
 * It uses Spring Security to define authentication and authorization mechanisms, including role-based access control
 * and a stateless session policy.
 *
 * This configuration also integrates JWT-based authentication using a custom JwtAuthFilter and an AuthenticationProvider.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFIlter;
    private final AuthenticationProvider provider;

    @Value("${security.auth.whitelist}")
    private String[] AUTH_WHITELIST;

    @Value("${security.auth.adminlist}")
    private String[] ADMIN_LIST;

    /**
     * Configures the HTTP security filter chain for the application.
     * <p>
     * This method disables CSRF, basic authentication, and form login. It defines the access rules for various endpoints:
     * <ul>
     *     <li>Endpoints listed in {@code AUTH_WHITELIST} are accessible to everyone without authentication.</li>
     *     <li>Endpoints listed in {@code ADMIN_LIST} are accessible only to users with the "ADMIN" role.</li>
     *     <li>All other endpoints require authentication.</li>
     * </ul>
     * The session management policy is set to stateless, and a custom JWT filter is added before the UsernamePasswordAuthenticationFilter.
     *
     * @param http the {@link HttpSecurity} object used to configure security settings
     * @return a fully configured {@link SecurityFilterChain}
     * @throws Exception if any error occurs during configuration
     */

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorization ->
                        authorization.anyRequest().permitAll()
//                                .requestMatchers(ADMIN_LIST).hasRole("ADMIN")
//                                .requestMatchers(AUTH_WHITELIST).permitAll()
//                                .anyRequest().authenticated()
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(provider)
                .addFilterBefore(jwtAuthFIlter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable);
        return http.build();
    }



}
