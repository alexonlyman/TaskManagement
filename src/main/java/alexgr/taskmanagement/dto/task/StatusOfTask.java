package alexgr.taskmanagement.dto.task;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Enumeration representing the status of a task.
 * <p>
 * This enum defines three possible statuses for a task: expectation, in process, and complete.
 * Each status is associated with a string value that can be used for display or storage purposes.
 * </p>
 */
@Schema(description = "Статусы задач, описывающие состояние выполнения задачи.")
public enum StatusOfTask {

    /**
     * Represents the status where the task is expected or planned.
     * <p>
     * Indicates that the task is not yet started but is scheduled or anticipated.
     * </p>
     */
    @Schema(description = "Ожидание: задача запланирована или ожидается, но еще не начата.", example = "expectation")
    EXPECTATION("expectation"),

    /**
     * Represents the status where the task is currently being worked on.
     * <p>
     * Indicates that the task is in progress and is being actively handled.
     * </p>
     */
    @Schema(description = "В процессе: задача находится в активной работе.", example = "in process")
    IN_PROCESS("in process"),

    /**
     * Represents the status where the task is finished.
     * <p>
     * Indicates that the task has been completed and no further action is required.
     * </p>
     */
    @Schema(description = "Завершено: задача полностью выполнена.", example = "complete")
    COMPLETE("complete");

    /**
     * The string value associated with the task status.
     */
    @Schema(description = "Строковое значение, представляющее статус задачи.", example = "expectation")
    public final String value;

    /**
     * Constructs a task status with the specified string value.
     *
     * @param value The string representation of the task status.
     */
    StatusOfTask(String value) {
        this.value = value;
    }

}
