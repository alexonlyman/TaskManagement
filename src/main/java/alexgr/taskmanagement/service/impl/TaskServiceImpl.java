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
import alexgr.taskmanagement.service.TaskService;
import alexgr.taskmanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

/**
 * Implementation of the {@link TaskService} interface.
 *
 * <p>This service provides methods for managing tasks, including creation, update, deletion,
 * and assignment of tasks to executors.</p>
 */
@Service
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepo taskRepo;
    private final UserRepo userRepo;
    private final UserService userService;
    private final TaskConvertor taskConvertor;
    private static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);


    /**
     * Creates a new task and associates it with the current user.
     *
     * @param taskDto the task details to be created.
     * @return the created {@link TaskEntity}.
     * @throws NameDuplicateException if a task with the same name already exists.
     */
    @Transactional
    @Override
    public TaskEntity createTask(Task taskDto) {
        validateInput(taskDto);

        if (taskRepo.existsByName(taskDto.getName())) {
            throw new NameDuplicateException("Task name is already in use");
        }

        Task task = new Task(
                taskDto.getId(),
                taskDto.getName(),
                taskDto.getStatusOfTask(),
                taskDto.getPriority(),
                taskDto.getDescription(),
                taskDto.getExecutor()
        );
        task.setDescription(taskDto.getDescription());
        task.setStatusOfTask(taskDto.getStatusOfTask());
        task.setName(taskDto.getName());
        task.setExecutor(taskDto.getExecutor());
        task.setPriority(taskDto.getPriority());

        TaskEntity entity = taskConvertor.convertToEntity(task);

        String user = userService.getCurrentUser();
        UserEntity userEntity = userRepo.findUserByEmail(user);
        entity.setUser(userEntity);
        entity.setExecutor(user);
        logger.info("task was saved by " + userEntity);
        taskRepo.save(entity);
        return entity;
    }

    /**
     * Updates a task's details if the current user is the executor of the task.
     *
     * @param id   the ID of the task to update.
     * @param task the new task details.
     * @return the updated {@link TaskEntity}.
     * @throws IdNotFoundException if the task with the given ID is not found.
     * @throws AccessDeniedException if the current user is not the executor of the task.
     */
    @Transactional
    public TaskEntity updateTaskByExecutor(Integer id, Task task) throws IdNotFoundException {
        String currentUser = userService.getCurrentUser();
        TaskEntity taskEntity = taskRepo.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Task with id " + id + " not found"));
        validateInput(task);
        if (!taskEntity.getExecutor().equals(currentUser)) {
            throw new AccessDeniedException("You are not the executor of this task.");
        }
        updateTaskFields(taskEntity, task);
        return taskRepo.save(taskEntity);
    }


    /**
     * Updates a task's details with administrator privileges.
     *
     * @param id   the ID of the task to update.
     * @param task the new task details.
     * @return the updated {@link TaskEntity}.
     * @throws IdNotFoundException if the task with the given ID is not found.
     */
    @Transactional
    public TaskEntity updateTaskByAdmin(Integer id, Task task) throws IdNotFoundException {
        TaskEntity taskEntity = taskRepo.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Task with id " + id + " not found"));
        validateInput(task);
        updateTaskFields(taskEntity, task);
        return taskRepo.save(taskEntity);
    }

    /**
     * Updates the fields of a task entity with the values from the given task DTO.
     *
     * @param taskEntity the task entity to update.
     * @param task       the task DTO containing new field values.
     */
    private void updateTaskFields(TaskEntity taskEntity, Task task) {
        taskEntity.setStatusOfTask(task.getStatusOfTask());
        taskEntity.setPriority(task.getPriority());
        taskEntity.setName(task.getName());
        taskEntity.setDescription(task.getDescription());
    }

    /**
     * Deletes a task by its ID.
     *
     * @param id the ID of the task to delete.
     * @throws IdNotFoundException if the task with the given ID is not found.
     */
    @Transactional
    public void deleteTask(Integer id) throws IdNotFoundException {
        TaskEntity task = taskRepo.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Task with id " + id + " not found"));

        taskRepo.delete(task);
        logger.info("Task with id {} was deleted", id);
    }

    /**
     * Assigns an executor to a task.
     *
     * @param taskId the ID of the task.
     * @param email  the email of the executor.
     * @throws IdNotFoundException if the task with the given ID is not found.
     */
    @Transactional
    public void appointAnExecutor(Integer taskId,String email) throws IdNotFoundException {
        TaskEntity entity = taskRepo.findById(taskId)
                .orElseThrow(() -> new IdNotFoundException("Task with id " + taskId + " not found"));

        entity.setExecutor(email);
        taskRepo.save(entity);
        logger.info("Executor {} appointed to task with id {}", email, taskId);
    }

    /**
     * Retrieves all tasks with pagination support.
     *
     * @param pageable the pagination information.
     * @return a paginated list of tasks.
     */
    public Page<Task> getAllTasks(Pageable pageable) {
        Page<TaskEntity> taskEntities = taskRepo.findAll(pageable);
        return taskEntities.map(task -> new Task(
                task.getId(),
                task.getName(),
                task.getStatusOfTask(),
                task.getPriority(),
                task.getDescription(),
                task.getExecutor()
        ));
    }

    /**
     * Searches for tasks based on status and priority filters.
     *
     * @param status   the task status filter.
     * @param priority the task priority filter.
     * @return a list of tasks matching the filters.
     */
    public List<TaskEntity> searchTasks(String status, String priority) {
        return taskRepo.findTasksByParams(status, priority);
    }


    /**
     * Validates the task input.
     *
     * @param task the task to validate.
     * @throws IllegalArgumentException if the task or its fields are invalid.
     */
    private static void validateInput(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task is null");
        }
        validatePriority(task.getPriority());
        validateStatus(task.getStatusOfTask());
    }


    private static void validatePriority(Priority priority) {
        if (priority == null || !EnumSet.allOf(Priority.class).contains(priority)) {
            throw new IllegalArgumentException("Invalid priority. Allowed values: " + Arrays.toString(Priority.values()));
        }
    }

    private static void validateStatus(StatusOfTask status) {
        if (status == null || !EnumSet.allOf(StatusOfTask.class).contains(status)) {
            throw new IllegalArgumentException("Invalid status. Allowed values: " + Arrays.toString(StatusOfTask.values()));
        }
    }
}
