package alexgr.taskmanagement.service.impl;

import alexgr.taskmanagement.dto.comment.Comment;
import alexgr.taskmanagement.entity.CommentEntity;
import alexgr.taskmanagement.entity.TaskEntity;
import alexgr.taskmanagement.entity.UserEntity;
import alexgr.taskmanagement.exceptions.IdNotFoundException;
import alexgr.taskmanagement.repository.CommentsRepo;
import alexgr.taskmanagement.repository.TaskRepo;
import alexgr.taskmanagement.repository.UserRepo;
import alexgr.taskmanagement.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

    @Mock
    private TaskRepo taskRepo;
    @Mock
    private UserRepo userRepo;
    @Mock
    private CommentsRepo commentsRepo;
    @Mock
    private UserService userService;

    @InjectMocks
    private CommentServiceImpl commentService;


    @Test
    void addComment_ShouldAddCommentSuccessfully() throws IdNotFoundException {

        Integer taskId = 1;
        Comment commentDto = new Comment();
        commentDto.setComment("This is a test comment");

        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setId(taskId);
        taskEntity.setName("Test Task");
        taskEntity.setCommentEntityList(new ArrayList<>());

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("test@example.com");

        when(taskRepo.findTaskEntityById(taskId)).thenReturn(taskEntity);
        when(userService.getCurrentUser()).thenReturn("test@example.com");
        when(userRepo.findUserByEmail("test@example.com")).thenReturn(userEntity);


        commentService.addComment(taskId, commentDto);


        verify(taskRepo, times(1)).findTaskEntityById(taskId);
        verify(userService, times(1)).getCurrentUser();
        verify(userRepo, times(1)).findUserByEmail("test@example.com");
        verify(commentsRepo, times(1)).save(any(CommentEntity.class));
        assertEquals(1, taskEntity.getCommentEntityList().size());
        assertEquals("This is a test comment", taskEntity.getCommentEntityList().get(0).getComment());
    }

    @Test
    void addComment_ShouldThrowException_WhenTaskNotFound() {
        // Arrange
        Integer taskId = 1;
        Comment commentDto = new Comment();
        commentDto.setComment("This is a test comment");

        when(taskRepo.findTaskEntityById(taskId)).thenReturn(null);

        // Act & Assert
        IdNotFoundException exception = assertThrows(IdNotFoundException.class,
                () -> commentService.addComment(taskId, commentDto));

        assertEquals("id not found", exception.getMessage());
        verify(taskRepo, times(1)).findTaskEntityById(taskId);
        verifyNoInteractions(userRepo, commentsRepo);
    }
}