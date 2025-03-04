package alexgr.taskmanagement.service.impl;

import alexgr.taskmanagement.dto.task.Priority;
import alexgr.taskmanagement.dto.task.StatusOfTask;
import alexgr.taskmanagement.dto.task.Task;
import alexgr.taskmanagement.entity.TaskEntity;
import alexgr.taskmanagement.entity.UserEntity;
import alexgr.taskmanagement.exceptions.IdNotFoundException;
import alexgr.taskmanagement.exceptions.NameDuplicateException;
import alexgr.taskmanagement.mapper.TaskConvertor;
import alexgr.taskmanagement.repository.TaskRepo;
import alexgr.taskmanagement.repository.UserRepo;
import alexgr.taskmanagement.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskRepo taskRepo;
    @Mock
    private UserRepo userRepo;
    @Mock
    private UserService userService;
    @Mock
    private TaskConvertor taskConvertor;

    @InjectMocks
    private TaskServiceImpl taskService;

    @Test
    void createTask_ShouldCreateTaskSuccessfully() {
        Task taskDto = new Task(1,"Test Task",StatusOfTask.EXPECTATION,Priority.MEDIUM_PRIORITY,
                "test@example.com","test@example.com");
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("test@example.com");

        TaskEntity taskEntity = new TaskEntity();

        when(taskRepo.existsByName(taskDto.getName())).thenReturn(false);
        when(userService.getCurrentUser()).thenReturn("test@example.com");
        when(userRepo.findUserByEmail("test@example.com")).thenReturn(userEntity);
        when(taskConvertor.convertToEntity(taskDto)).thenReturn(taskEntity);

        TaskEntity createdTask = taskService.createTask(taskDto);

        verify(taskRepo, times(1)).existsByName(taskDto.getName());
        verify(taskConvertor, times(1)).convertToEntity(taskDto);
        verify(userRepo, times(1)).findUserByEmail("test@example.com");
        verify(taskRepo, times(1)).save(taskEntity);
        assertEquals(userEntity, taskEntity.getUser());
        assertEquals("test@example.com", taskEntity.getExecutor());
    }

}