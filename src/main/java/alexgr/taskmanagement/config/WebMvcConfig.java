package alexgr.taskmanagement.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/**
 * WebMvcConfig is a configuration class that customizes the behavior of Spring MVC.
 * <p>
 * This class configures CORS (Cross-Origin Resource Sharing) settings and sets up OpenAPI documentation
 * for the application. It ensures that the application can handle cross-origin requests and provides
 * API documentation with security integration.
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * Configures CORS (Cross-Origin Resource Sharing) mappings.
     * <p>
     * This method allows cross-origin requests from the specified origin (e.g., a frontend application running on localhost:3000).
     * It permits various HTTP methods and headers and enables credentials (cookies or authorization headers) to be sent with requests.
     *
     * @param registry the {@link CorsRegistry} used to define CORS mappings
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000", "http://localhost:8080")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "HEAD")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

    /**
     * Configures OpenAPI documentation for the application.
     * <p>
     * This method sets up the OpenAPI specification with information about the API and security schemes.
     * It adds a security scheme for JWT-based authentication using the "Bearer" token format.
     *
     * @return an {@link OpenAPI} object containing API documentation configuration
     */
    @Bean
    public OpenAPI myOpenAPI() {
        final String securitySchemeName = "BearerAuth";
        return new OpenAPI()
                .info(new Info()
                        .title("Task Management API Documentation")
                        .version("1.0")
                        .description("Spring Boot RESTful application"))
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(
                        new Components()
                                .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT"))
                );
    }

}
