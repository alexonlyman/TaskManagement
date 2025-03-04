package alexgr.taskmanagement.service.impl;

import alexgr.taskmanagement.dto.auth.AuthPayload;
import alexgr.taskmanagement.dto.role.Role;
import alexgr.taskmanagement.dto.user.User;
import alexgr.taskmanagement.entity.RoleEntity;
import alexgr.taskmanagement.entity.UserEntity;
import alexgr.taskmanagement.exceptions.WrongLoginPasswordException;
import alexgr.taskmanagement.mapper.UserConvertor;
import alexgr.taskmanagement.repository.RoleRepo;
import alexgr.taskmanagement.repository.UserRepo;
import alexgr.taskmanagement.utils.AuthenticationResponse;
import alexgr.taskmanagement.utils.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class responsible for handling user authentication and registration.
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService tokenService;
    private final UserConvertor userConvertor;
    private final AuthenticationManager authenticationManager;
    private final RoleRepo roleRepo;
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    /**
     * Registers a new user with the provided authentication payload.
     *
     * @param authPayload the authentication payload containing the user's email and password.
     * @return an {@link AuthenticationResponse} containing a JWT token for the registered user.
     * @throws RuntimeException if the user's role cannot be found in the role repository.
     */
    public AuthenticationResponse register(AuthPayload authPayload) {
        logger.info("Registering user with email: {}", authPayload.getEmail());
        User user = new User();
        user.setEmail(authPayload.getEmail());
        user.setPassword(passwordEncoder.encode(authPayload.getPassword()));
        RoleEntity roleEntity = roleRepo.findByRole(Role.USER).orElseThrow(() -> new RuntimeException("no roles match"));

        UserEntity entity = userConvertor.convertToEntity(user);
        entity.setRole(roleEntity.getRole());
        userRepo.save(entity);
        logger.info("User registered successfully.");

        String token = tokenService.generateToken(new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), new ArrayList<>()));

        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    /**
     * Authenticates a user with the provided authentication payload.
     *
     * @param authPayload the authentication payload containing the user's email and password.
     * @return an {@link AuthenticationResponse} containing a JWT token for the authenticated user.
     * @throws org.springframework.security.authentication.BadCredentialsException if authentication fails.
     */
    public AuthenticationResponse authenticate(AuthPayload authPayload) {
        logger.info("Authenticating user with email: {}", authPayload.getEmail());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authPayload.getEmail(), authPayload.getPassword())
        );

        UserEntity userEntity = userRepo.findUserByEmail(authPayload.getEmail());

        List<GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + userEntity.getRole().name())
        );

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                userEntity.getEmail(), userEntity.getPassword(), authorities);

        String token = tokenService.generateToken(userDetails);

        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }
}
