package alexgr.taskmanagement.controller;

import alexgr.taskmanagement.dto.comment.Comment;
import alexgr.taskmanagement.dto.task.Task;
import alexgr.taskmanagement.entity.TaskEntity;
import alexgr.taskmanagement.exceptions.IdNotFoundException;
import alexgr.taskmanagement.service.CommentService;
import alexgr.taskmanagement.service.UserService;
import alexgr.taskmanagement.service.impl.TaskServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;
/**
 * REST controller for managing tasks, comments, and user-related operations.
 * <p>
 * This controller provides endpoints for creating, updating, deleting, and searching tasks,
 * as well as managing comments and assigning task executors.
 * </p>
 */
@RestController()
@RequestMapping("/task")
@Tag(name = "Task Controller", description = "API для управления задачами")
@RequiredArgsConstructor
@CrossOrigin(value = "http://localhost:8080")
public class TaskController {

    private final TaskServiceImpl taskService;
    private final CommentService commentService;
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    /**
     * Creates a new task.
     *
     * @param taskDTO the {@link Task} object containing the details of the task to be created
     * @return a {@link ResponseEntity} containing the created {@link TaskEntity}
     */
    @Operation(summary = "Создать новую задачу", description = "Создает новую задачу на основе переданного объекта TaskDTO.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задача успешно создана",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskEntity.class))}),
            @ApiResponse(responseCode = "400", description = "Неверные данные запроса", content = @Content)
    })
    @PostMapping("/create")
    public ResponseEntity<?> createTask(@RequestBody @Valid Task taskDTO) {
        logger.info("Creating task: {}", taskDTO);
        TaskEntity createdTask = taskService.createTask(taskDTO);
        return ResponseEntity.ok(createdTask);
    }

    /**
     * Updates a task by its executor.
     *
     * @param id      the ID of the task to update
     * @param taskDTO the {@link Task} object containing the updated task details
     * @return a {@link ResponseEntity} containing the updated {@link TaskEntity}
     * @throws IdNotFoundException if the task with the specified ID is not found
     */
    @Operation(summary = "Обновить задачу исполнителем", description = "Обновляет задачу по её ID исполнителем.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задача успешно обновлена",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskEntity.class))}),
            @ApiResponse(responseCode = "404", description = "Задача не найдена", content = @Content)
    })
    @PatchMapping("/update/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Integer id, @RequestBody @Valid Task taskDTO) throws IdNotFoundException {
        logger.info("Updating task with ID {}: {}", id, taskDTO);
        TaskEntity updatedTask = taskService.updateTaskByExecutor(id, taskDTO);
        return ResponseEntity.ok(updatedTask);
    }

    /**
     * Updates a task by an admin.
     *
     * @param id      the ID of the task to update
     * @param taskDTO the {@link Task} object containing the updated task details
     * @return a {@link ResponseEntity} containing the updated {@link TaskEntity}
     * @throws IdNotFoundException if the task with the specified ID is not found
     */
    @Operation(summary = "Обновить задачу администратором", description = "Обновляет задачу по её ID администратором.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задача успешно обновлена",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskEntity.class))}),
            @ApiResponse(responseCode = "404", description = "Задача не найдена", content = @Content)
    })
    @PatchMapping("/update_admin/{id}")
    public ResponseEntity<?> updateTaskByAdmin(@PathVariable Integer id, @RequestBody @Valid Task taskDTO) throws IdNotFoundException {
        logger.info("Updating task by admin with ID {}: {}", id, taskDTO);
        TaskEntity updatedTask = taskService.updateTaskByAdmin(id, taskDTO);
        return ResponseEntity.ok(updatedTask);
    }

    /**
     * Deletes a task by its ID.
     *
     * @param id the ID of the task to delete
     * @return a {@link ResponseEntity} with a success message
     * @throws IdNotFoundException if the task with the specified ID is not found
     */
    @Operation(summary = "Удалить задачу", description = "Удаляет задачу по её ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задача успешно удалена", content = @Content),
            @ApiResponse(responseCode = "404", description = "Задача не найдена", content = @Content)
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Integer id) throws IdNotFoundException {
        logger.info("Deleting task with ID {}", id);
        taskService.deleteTask(id);
        return ResponseEntity.ok(createMessageResponse("Задача удалена"));
    }

    /**
     * Adds a comment to a task.
     *
     * @param id      the ID of the task to add a comment to
     * @param comment the {@link Comment} object containing the comment details
     * @return a {@link ResponseEntity} with a success message
     * @throws IdNotFoundException if the task with the specified ID is not found
     */
    @Operation(summary = "Добавить комментарий к задаче", description = "Добавляет комментарий к задаче по её ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Комментарий успешно добавлен", content = @Content),
            @ApiResponse(responseCode = "404", description = "Задача не найдена", content = @Content)
    })
    @PostMapping("/comment/{id}")
    public ResponseEntity<?> addComment(@PathVariable Integer id, @RequestBody @Valid Comment comment) throws IdNotFoundException {
        logger.info("Adding comment" + comment);
        commentService.addComment(id, comment);
        return ResponseEntity.ok(createMessageResponse("Комментарий добавлен"));
    }

    /**
     * Retrieves all tasks with pagination.
     *
     * @param pageable the {@link Pageable} object for pagination details
     * @return a {@link ResponseEntity} containing a paginated list of {@link Task}
     */
    @Operation(summary = "Получить все задачи с пагинацией", description = "Возвращает список всех задач с пагинацией.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список задач успешно получен",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class))}),
    })
    @GetMapping("/allPaged")
    public ResponseEntity<Page<Task>> getAllTasksPaged(Pageable pageable) {
        Page<Task> tasks = taskService.getAllTasks(pageable);
        return ResponseEntity.ok(tasks);
    }

    /**
     * Retrieves a list of all users.
     *
     * @return a {@link ResponseEntity} containing a list of user names
     */
    @Operation(summary = "Получить список всех пользователей", description = "Возвращает список всех пользователей.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список пользователей успешно получен",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = List.class))}),
    })
    @GetMapping("/all_users")
    public ResponseEntity<List<String>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    /**
     * Assigns an executor to a task.
     *
     * @param id    the ID of the task to assign an executor to
     * @param email the email of the user to assign as the executor
     * @return a {@link ResponseEntity} with a success message
     * @throws IdNotFoundException if the task with the specified ID is not found
     */
    @Operation(summary = "Назначить исполнителя задаче", description = "Назначает исполнителя задаче по её ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Исполнитель успешно назначен", content = @Content),
            @ApiResponse(responseCode = "404", description = "Задача не найдена", content = @Content)
    })
    @PutMapping("/assign/{id}")
    public ResponseEntity<?> assignExecutor(@PathVariable Integer id, @RequestBody @Valid String email) throws IdNotFoundException {
        logger.info("Assigning executor '{}' to task with ID {}", email, id);
        taskService.appointAnExecutor(id,email);
        return ResponseEntity.ok(createMessageResponse("Исполнитель назначен: " + email));
    }

    /**
     * Searches for tasks by status and priority.
     *
     * @param status   the status to filter tasks by (optional)
     * @param priority the priority to filter tasks by (optional)
     * @return a {@link ResponseEntity} containing a list of matching {@link TaskEntity}
     */
    @Operation(summary = "Поиск задач по статусу и приоритету", description = "Возвращает список задач, соответствующих заданным статусу и приоритету.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список задач успешно получен",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = List.class))}),
    })
    @GetMapping("/search")
    public ResponseEntity<List<TaskEntity>> searchTasks(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String priority
    ) {
        List<TaskEntity> tasks = taskService.searchTasks(status, priority);
        return ResponseEntity.ok(tasks);
    }

    /**
     * Creates a message response as a map.
     *
     * @param message the message to include in the response
     * @return a map containing the message
     */

    private Map<String, String> createMessageResponse(String message) {
        return Map.of("message", message);
    }


}
