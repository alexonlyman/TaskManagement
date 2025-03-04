package alexgr.taskmanagement.entity;


import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * Entity class representing a comment made by a user on a specific task.
 * <p>
 * This class maps to the "comments" table in the database and includes fields
 * for the comment's unique identifier, content, associated user, associated task,
 * creation date, last modified date, and version. It is annotated with JPA
 * annotations to define the entity and its relationships.
 * </p>
 */
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "comments")
public class CommentEntity {

    /**
     * The unique identifier for the comment.
     * <p>
     * This ID is generated automatically and uniquely identifies each comment.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * The content or text of the comment.
     * <p>
     * Represents the actual message or feedback provided by the user.
     * This field is mandatory.
     * </p>
     */
    @Column(name = "comment", nullable = false)
    private String comment;

    /**
     * The user who made the comment.
     * <p>
     * This field references the user responsible for creating the comment.
     * It is mapped to the "user_id" column in the database.
     * </p>
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    /**
     * The task to which the comment is associated.
     * <p>
     * This field references the task that the comment is related to.
     * It is mapped to the "task_id" column in the database.
     * </p>
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    private TaskEntity task;

    /**
     * The date and time when the comment was created.
     * <p>
     * This field is automatically set to the current date and time when the comment is created.
     * It is mapped to the "created_date" column in the database and is not updatable.
     * </p>
     */
    @CreatedDate
    @Column(name = "created_date", updatable = false, nullable = false)
    private LocalDateTime createdDate;

    /**
     * The date and time when the comment was last modified.
     * <p>
     * This field is automatically updated to the current date and time whenever the comment is modified.
     * It is mapped to the "updated_date" column in the database.
     * </p>
     */
    @LastModifiedDate
    @Column(name = "updated_date", nullable = false)
    private LocalDateTime updatedDate;

    /**
     * The version of the comment entity.
     * <p>
     * This field is used for optimistic locking to prevent concurrent modifications.
     * It is mapped to the "version" column in the database.
     * </p>
     */
    @Version
    @Column(name = "version", nullable = false)
    private int version;

}
