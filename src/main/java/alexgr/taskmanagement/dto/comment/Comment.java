package alexgr.taskmanagement.dto.comment;

import alexgr.taskmanagement.dto.task.Task;
import alexgr.taskmanagement.dto.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Objects;
/**
 * Data Transfer Object (DTO) representing a comment made by a user on a specific task.
 * <p>
 * This class encapsulates the details of a comment, including its unique identifier,
 * the content of the comment, the user who made the comment, and the task to which
 * the comment is associated.
 * </p>
 */
@Data
@Schema(description = "Модель комментария, представляющая сообщение, связанное с задачей и пользователем.")
public class Comment {

    /**
     * The unique identifier for the comment.
     * <p>
     * This ID is used to distinguish one comment from another.
     * </p>
     */
    @Schema(description = "Уникальный идентификатор комментария.", example = "1")
    private Integer id;

    /**
     * The content or text of the comment.
     * <p>
     * Represents the actual message or feedback provided by the user.
     * </p>
     */
    @Schema(description = "Текст комментария, предоставленный пользователем.", example = "Это пример комментария.")
    private String comment;

    /**
     * The user who made the comment.
     * <p>
     * This field references the user responsible for creating the comment.
     * </p>
     */
    @Schema(description = "Пользователь, который оставил комментарий. Ссылка на объект пользователя.")
    private User user;

    /**
     * The task to which the comment is associated.
     * <p>
     * This field references the task that the comment is related to.
     * </p>
     */
    @Schema(description = "Задача, к которой относится комментарий. Ссылка на объект задачи.")
    private Task task;
}
