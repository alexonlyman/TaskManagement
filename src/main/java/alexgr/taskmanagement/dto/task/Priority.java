package alexgr.taskmanagement.dto.task;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Enumeration representing the priority levels of a task or item.
 * <p>
 * This enum defines three levels of priority: low, medium, and high. Each priority
 * level is associated with a string value that can be used for display or storage
 * purposes.
 * </p>
 */
@Schema(description = "Уровни приоритета, используемые для классификации задач или элементов.")
public enum Priority {
    /**
     * Represents a low priority level.
     * <p>
     * Indicates that the task or item has the lowest urgency or importance.
     * </p>
     */
    @Schema(description = "Низкий уровень приоритета. Указывает, что задача имеет наименьшую срочность или важность.", example = "low")
    LOW_PRIORITY("low"),

    /**
     * Represents a medium priority level.
     * <p>
     * Indicates that the task or item has a moderate level of urgency or importance.
     * </p>
     */
    @Schema(description = "Средний уровень приоритета. Указывает на умеренную срочность или важность задачи.", example = "medium")
    MEDIUM_PRIORITY("medium"),

    /**
     * Represents a high priority level.
     * <p>
     * Indicates that the task or item has the highest urgency or importance.
     * </p>
     */
    @Schema(description = "Высокий уровень приоритета. Указывает на наивысшую срочность или важность задачи.", example = "high")
    HIGH_PRIORITY("high");

    /**
     * The string value associated with the priority level.
     */
    @Schema(description = "Строковое значение, связанное с уровнем приоритета.", example = "low")
    public final String value;

    /**
     * Constructs a priority level with the specified string value.
     *
     * @param value The string representation of the priority level.
     */
    Priority(String value) {
        this.value = value;
    }
}
