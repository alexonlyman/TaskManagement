package alexgr.taskmanagement.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration class for setting up authentication and password encoding in a Spring application.
 * This class is annotated with {@code @Configuration} to indicate that it contains bean definitions
 * to be managed by the Spring container. It uses {@code @RequiredArgsConstructor} to generate a
 * constructor with required arguments for final fields and {@code @EnableJpaAuditing} to enable
 * JPA auditing features.
 *
 * <p>The class configures the following beans:</p>
 * <ul>
 *     <li>{@code AuthenticationProvider}: A bean responsible for authenticating users using
 *     a {@code UserDetailsService} and a {@code PasswordEncoder}.</li>
 *     <li>{@code AuthenticationManager}: A bean that manages the process of authenticating users.</li>
 *     <li>{@code PasswordEncoder}: A bean that encodes passwords using the BCrypt hashing algorithm.</li>
 * </ul>
 *
 * @author [AlexKha]
 * @version 1.0
 */
@Configuration
@RequiredArgsConstructor
@EnableJpaAuditing
public class ApplicationConfig {
    private final UserDetailsService userDetailsService;

    /**
     * Creates and configures a DaoAuthenticationProvider bean.
     * This bean is responsible for authenticating users using the UserDetailsService
     * and encoding passwords with the specified PasswordEncoder.
     *
     * @return Configured DaoAuthenticationProvider instance.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    /**
     * Creates an AuthenticationManager bean using the provided AuthenticationConfiguration.
     * The AuthenticationManager is responsible for processing authentication requests.
     *
     * @param configuration The AuthenticationConfiguration to retrieve the AuthenticationManager from.
     * @return Configured AuthenticationManager instance.
     * @throws Exception if the AuthenticationManager cannot be created.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

