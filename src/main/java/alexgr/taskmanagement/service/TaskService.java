package alexgr.taskmanagement.service;

import alexgr.taskmanagement.dto.task.Task;
import alexgr.taskmanagement.entity.TaskEntity;
import alexgr.taskmanagement.exceptions.IdNotFoundException;
import alexgr.taskmanagement.exceptions.NameDuplicateException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service interface for managing tasks.
 */
public interface TaskService {

    /**
     * Creates a new task based on the provided task DTO.
     *
     * @param taskDto the task DTO containing the details of the new task.
     * @return the created TaskEntity.
     */
    TaskEntity createTask(Task taskDto);

    /**
     * Updates a task's details if the current user is its executor.
     *
     * @param id   the ID of the task to update.
     * @param task the task DTO containing the updated details.
     * @return the updated TaskEntity.
     * @throws IdNotFoundException if the task with the specified ID is not found.
     */
    TaskEntity updateTaskByExecutor(Integer id, Task task) throws IdNotFoundException;

    /**
     * Updates a task's details as an administrator.
     *
     * @param id   the ID of the task to update.
     * @param task the task DTO containing the updated details.
     * @return the updated TaskEntity.
     * @throws IdNotFoundException if the task with the specified ID is not found.
     */
    TaskEntity updateTaskByAdmin(Integer id, Task task) throws IdNotFoundException;

    /**
     * Deletes a task specified by its ID.
     *
     * @param id the ID of the task to delete.
     * @throws IdNotFoundException if the task with the specified ID is not found.
     */
    void deleteTask(Integer id) throws IdNotFoundException;

    /**
     * Assigns an executor to a task by its ID.
     *
     * @param taskId the ID of the task.
     * @param email  the email of the executor to assign.
     * @throws IdNotFoundException if the task with the specified ID is not found.
     */
    void appointAnExecutor(Integer taskId, String email) throws IdNotFoundException;

    /**
     * Retrieves a paginated list of all tasks.
     *
     * @param pageable the pagination information.
     * @return a paginated list of tasks.
     */
    Page<Task> getAllTasks(Pageable pageable);

    /**
     * Searches for tasks based on their status and priority.
     *
     * @param status   the status of the tasks to search for (optional).
     * @param priority the priority of the tasks to search for (optional).
     * @return a list of tasks matching the specified parameters.
     */
    List<TaskEntity> searchTasks(String status, String priority);
}

