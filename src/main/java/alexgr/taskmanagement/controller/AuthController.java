package alexgr.taskmanagement.controller;

import alexgr.taskmanagement.dto.auth.AuthPayload;
import alexgr.taskmanagement.service.impl.AuthService;
import alexgr.taskmanagement.utils.AuthenticationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
/**
 * AuthController is a REST controller responsible for handling user authentication and registration requests.
 * <p>
 * It provides endpoints for user registration and authentication, and logs authentication-related events.
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Handles user registration and authentication")
public class AuthController {

    private final AuthService authService;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    /**
     * Handles user registration requests.
     * <p>
     * This endpoint accepts a JSON payload containing user credentials and passes it to the {@link AuthService} for processing.
     *
     * @param authPayload the {@link AuthPayload} containing user registration details
     * @return a {@link ResponseEntity} containing the {@link AuthenticationResponse} with registration details
     */
    @Operation(summary = "Register a new user", description = "Handles user registration requests")
    @ApiResponse(responseCode = "200", description = "User successfully registered")
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthPayload authPayload) {
        return ResponseEntity.ok(authService.register(authPayload));
    }

    /**
     * Handles user authentication requests.
     * <p>
     * This endpoint accepts a JSON payload containing user credentials, logs the request, and passes it to the
     * {@link AuthService} for authentication.
     *
     * @param authPayload the {@link AuthPayload} containing user login details
     * @return a {@link ResponseEntity} containing the {@link AuthenticationResponse} with authentication details
     */
    @Operation(summary = "Authenticate user", description = "Handles user authentication requests")
    @ApiResponse(responseCode = "200", description = "User successfully authenticated")
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> auth(@RequestBody AuthPayload authPayload) {
        logger.trace("данные получены");
        return ResponseEntity.ok(authService.authenticate(authPayload));
    }

}
