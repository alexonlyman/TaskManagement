package alexgr.taskmanagement.entity;

import alexgr.taskmanagement.dto.task.Priority;
import alexgr.taskmanagement.dto.task.StatusOfTask;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity class representing a task in the system.
 * <p>
 * This class maps to the "tasks" table in the database and includes fields
 * for the task's unique identifier, status, priority, name, description, executor,
 * associated user, associated comments, creation date, last modified date, and version.
 * It is annotated with JPA annotations to define the entity and its relationships.
 * </p>
 */
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tasks")
public class TaskEntity {

    /**
     * The unique identifier for the task.
     * <p>
     * This ID is generated automatically and uniquely identifies each task.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * The status of the task.
     * <p>
     * Represents the current status of the task, such as expectation, in process, or complete.
     * This field is mandatory and is mapped to the "status" column in the database.
     * </p>
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusOfTask statusOfTask;

    /**
     * The priority of the task.
     * <p>
     * Represents the priority level of the task, such as low, medium, or high.
     * This field is mandatory and is mapped to the "priority" column in the database.
     * </p>
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false)
    private Priority priority;

    /**
     * The name of the task.
     * <p>
     * Represents the title or name given to the task. This field is mandatory.
     * </p>
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * The description of the task.
     * <p>
     * Provides additional details or information about the task. This field is optional.
     * </p>
     */
    @Column(name = "description")
    private String description;

    /**
     * The executor of the task.
     * <p>
     * Represents the person or entity responsible for executing the task. This field is optional.
     * </p>
     */
    @Column(name = "executor")
    private String executor;

    /**
     * The user associated with the task.
     * <p>
     * This field references the user who created or is responsible for the task.
     * It is mapped to the "user_id" column in the database.
     * </p>
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    /**
     * The list of comments associated with the task.
     * <p>
     * Represents the comments made on the task. This field is mapped by the "task" field
     * in the CommentEntity class and supports cascading and orphan removal.
     * </p>
     */
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentEntity> commentEntityList = new ArrayList<>();

    /**
     * The date and time when the task was created.
     * <p>
     * This field is automatically set to the current date and time when the task is created.
     * It is mapped to the "created_date" column in the database and is not updatable.
     * </p>
     */
    @CreatedDate
    @Column(name = "created_date", updatable = false, nullable = false)
    private LocalDateTime createdDate;

    /**
     * The date and time when the task was last modified.
     * <p>
     * This field is automatically updated to the current date and time whenever the task is modified.
     * It is mapped to the "updated_date" column in the database.
     * </p>
     */
    @LastModifiedDate
    @Column(name = "updated_date", nullable = false)
    private LocalDateTime updatedDate;

    /**
     * The version of the task entity.
     * <p>
     * This field is used for optimistic locking to prevent concurrent modifications.
     * It is mapped to the "version" column in the database.
     * </p>
     */
    @Version
    @Column(name = "version", nullable = false)
    private int version;

}

