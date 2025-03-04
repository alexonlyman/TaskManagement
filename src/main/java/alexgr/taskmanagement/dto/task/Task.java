package alexgr.taskmanagement.dto.task;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
/**
 * Represents a task in the application.
 * <p>
 * A task contains various details such as its name, status, priority, description, and executor information.
 * </p>
 */
@Data
@AllArgsConstructor
@Schema(description = "Модель задачи, представляющая основные свойства задачи, такие как статус, приоритет и исполнитель.")
public class Task {

    /**
     * The unique identifier of the task.
     */
    @Schema(description = "Уникальный идентификатор задачи.", example = "1")
    private Integer id;

    /**
     * The name of the task.
     */
    @Schema(description = "Название задачи.", example = "Реализация API для управления задачами")
    private String name;

    /**
     * The current status of the task.
     * <p>
     * It is represented by the {@link StatusOfTask} enum.
     * </p>
     */
    @Schema(description = "Текущий статус задачи. Использует перечисление StatusOfTask.", example = "IN_PROCESS")
    private StatusOfTask statusOfTask;

    /**
     * The priority of the task.
     * <p>
     * It is represented by the {@link Priority} enum.
     * </p>
     */
    @Schema(description = "Приоритет задачи. Использует перечисление Priority.", example = "HIGH_PRIORITY")
    private Priority priority;

    /**
     * A brief description of the task.
     */
    @Schema(description = "Краткое описание задачи.", example = "Необходимо реализовать функциональность для обновления статуса задачи.")
    private String description;

    /**
     * The email or identifier of the executor assigned to the task.
     */
    @Schema(description = "Email или идентификатор исполнителя, назначенного на задачу.", example = "executor@example.com")
    private String executor;

}

