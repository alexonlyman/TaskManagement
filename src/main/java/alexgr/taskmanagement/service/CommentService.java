package alexgr.taskmanagement.service;

import alexgr.taskmanagement.dto.comment.Comment;
import alexgr.taskmanagement.exceptions.IdNotFoundException;

/**
 * Service interface for managing comments associated with tasks.
 */
public interface CommentService {

    /**
     * Adds a comment to a task specified by its ID.
     *
     * @param taskId  the ID of the task to which the comment will be added.
     * @param comment the comment object containing the comment details.
     * @throws IdNotFoundException if the task with the specified ID is not found.
     */
    public void addComment(Integer taskId, Comment comment) throws IdNotFoundException;
}
