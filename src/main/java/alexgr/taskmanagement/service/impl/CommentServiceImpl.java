package alexgr.taskmanagement.service.impl;

import alexgr.taskmanagement.dto.comment.Comment;
import alexgr.taskmanagement.entity.CommentEntity;
import alexgr.taskmanagement.entity.TaskEntity;
import alexgr.taskmanagement.entity.UserEntity;
import alexgr.taskmanagement.exceptions.IdNotFoundException;
import alexgr.taskmanagement.repository.CommentsRepo;
import alexgr.taskmanagement.repository.TaskRepo;
import alexgr.taskmanagement.repository.UserRepo;
import alexgr.taskmanagement.service.CommentService;
import alexgr.taskmanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
/**
 * Implementation of the {@link CommentService} interface.
 *
 * <p>This service handles operations related to comments, including adding comments to tasks.</p>
 */
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);
    private final TaskRepo taskRepo;
    private final UserRepo userRepo;
    private final CommentsRepo commentsRepo;
    private final UserService userService;

    /**
     * Adds a comment to a specific task.
     *
     * @param taskId     the ID of the task to which the comment should be added.
     * @param commentDto the comment data transfer object containing the comment content.
     * @throws IdNotFoundException if the task with the specified ID is not found.
     */
    @Override
    public void addComment(Integer taskId, Comment commentDto) throws IdNotFoundException {
        TaskEntity entity = taskRepo.findTaskEntityById(taskId);
        logger.info("task id " + taskId);
        if (entity != null) {
            String user = userService.getCurrentUser();
            UserEntity userEntity = userRepo.findUserByEmail(user);
            userEntity.setEmail(user);
            entity.setId(taskId);
            CommentEntity comment = new CommentEntity();
            comment.setComment(commentDto.getComment());
            comment.setUser(userEntity);
            comment.setTask(entity);
            entity.getCommentEntityList().add(comment);
            commentsRepo.save(comment);
            logger.info("comment id" + comment.getId());
            logger.info("user" + userEntity.getEmail());
            logger.info("task" + entity.getName());
        } else {
            throw new IdNotFoundException("id not found");
        }
    }
}
