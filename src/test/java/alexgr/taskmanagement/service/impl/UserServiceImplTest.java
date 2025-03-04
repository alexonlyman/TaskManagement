package alexgr.taskmanagement.service.impl;

import alexgr.taskmanagement.dto.user.UpdateUser;
import alexgr.taskmanagement.entity.UserEntity;
import alexgr.taskmanagement.exceptions.UserEmailNotFoundException;
import alexgr.taskmanagement.repository.UserRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private UserServiceImpl userService;
    @Test
    void updateUserInfo_ShouldUpdateUserSuccessfully() {

        UpdateUser updateUser = new UpdateUser();
        updateUser.setEmail("test@example.com");
        updateUser.setFirstName("UpdatedFirstName");
        updateUser.setLastName("UpdatedLastName");

        UserEntity existingUser = new UserEntity();
        existingUser.setEmail("test@example.com");
        existingUser.setFirstName("OldFirstName");
        existingUser.setLastName("OldLastName");

        when(userRepo.findUserByEmail("test@example.com")).thenReturn(existingUser);

        UpdateUser result = userService.updateUserInfo(updateUser);

        verify(userRepo, times(1)).findUserByEmail("test@example.com");
        verify(userRepo, times(1)).save(existingUser);
        assertEquals("UpdatedFirstName", existingUser.getFirstName());
        assertEquals("UpdatedLastName", existingUser.getLastName());
        assertEquals(updateUser, result);
    }

    @Test
    void updateUserInfo_ShouldThrowException_WhenUserNotFound() {

        UpdateUser updateUser = new UpdateUser();
        updateUser.setEmail("nonexistent@example.com");

        when(userRepo.findUserByEmail("nonexistent@example.com")).thenReturn(null);


        UserEmailNotFoundException exception = assertThrows(UserEmailNotFoundException.class,
                () -> userService.updateUserInfo(updateUser));

        assertEquals("Email not found: nonexistent@example.com", exception.getMessage());
        verify(userRepo, times(1)).findUserByEmail("nonexistent@example.com");
        verifyNoMoreInteractions(userRepo);
    }
}