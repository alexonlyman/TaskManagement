package alexgr.taskmanagement.service.impl;

import alexgr.taskmanagement.dto.auth.AuthPayload;
import alexgr.taskmanagement.dto.role.Role;
import alexgr.taskmanagement.dto.user.User;
import alexgr.taskmanagement.entity.RoleEntity;
import alexgr.taskmanagement.entity.UserEntity;
import alexgr.taskmanagement.mapper.UserConvertor;
import alexgr.taskmanagement.repository.RoleRepo;
import alexgr.taskmanagement.repository.UserRepo;
import alexgr.taskmanagement.utils.AuthenticationResponse;
import alexgr.taskmanagement.utils.JwtTokenService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    @Mock
    private UserRepo userRepo;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtTokenService tokenService;
    @Mock
    private UserConvertor userConvertor;
    @Mock
    private RoleRepo roleRepo;

    @InjectMocks
    private AuthService authService;

    @Test
    void register_ShouldRegisterUserSuccessfully() {
        AuthPayload authPayload = new AuthPayload();
        authPayload.setEmail("test@example.com");
        authPayload.setPassword("password123");
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(1);
        roleEntity.setRole(Role.USER);
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(authPayload.getEmail());

        when(roleRepo.findByRole(Role.USER)).thenReturn(Optional.of(roleEntity));
        when(passwordEncoder.encode(authPayload.getPassword())).thenReturn("encodedPassword");
        when(userConvertor.convertToEntity(any(User.class))).thenReturn(userEntity);
        when(tokenService.generateToken(any())).thenReturn("testToken");

        AuthenticationResponse response = authService.register(authPayload);

        assertNotNull(response);
        assertEquals("testToken", response.getToken());
        verify(userRepo, times(1)).save(userEntity);
        verify(roleRepo, times(1)).findByRole(Role.USER);
        verify(passwordEncoder, times(1)).encode(authPayload.getPassword());
        verify(tokenService, times(1)).generateToken(any());


    }
}