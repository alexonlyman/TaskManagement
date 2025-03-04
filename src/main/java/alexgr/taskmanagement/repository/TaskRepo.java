package alexgr.taskmanagement.repository;

import alexgr.taskmanagement.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository interface for managing {@link TaskEntity} entities.
 *
 * <p>Extends {@link JpaRepository} to provide CRUD operations and custom query methods for {@link TaskEntity}.</p>
 *
 * @see JpaRepository
 */
public interface TaskRepo extends JpaRepository<TaskEntity,Integer> {

    /**
     * Finds a {@link TaskEntity} by its unique identifier.
     *
     * @param id the unique identifier of the task.
     * @return the {@link TaskEntity} with the specified ID, or {@code null} if no entity is found.
     */
    TaskEntity findTaskEntityById(Integer id);

    /**
     * Checks if a task with the specified name exists.
     *
     * @param name the name of the task to check.
     * @return {@code true} if a task with the specified name exists, {@code false} otherwise.
     */
    boolean existsByName(String name);

    /**
     * Finds a list of {@link TaskEntity} based on the specified parameters.
     *
     * <p>This query allows filtering by task status and priority. The filters are optional:
     * - If {@code status} is {@code null}, the status condition is ignored.
     * - If {@code priority} is {@code null}, the priority condition is ignored.</p>
     *
     * @param status the status of the tasks to filter (optional).
     * @param priority the minimum priority of the tasks to filter (optional).
     * @return a list of {@link TaskEntity} that match the given parameters.
     */
    @Query("SELECT t FROM TaskEntity t WHERE " +
            "(:status IS NULL OR t.statusOfTask = :status) AND " +
            "(:priority IS NULL OR t.priority > :priority)")
    List<TaskEntity> findTasksByParams(
            @Param("status") String status,
            @Param("priority") String priority
    );


}
