package alexgr.taskmanagement.dto.role;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Enum representing user roles within the application.
 * <p>
 * This enum is used to define and manage different levels of access in the system.
 * </p>
 */
@Schema(description = "Роли, доступные в системе, определяющие уровень доступа пользователя.")
public enum Role {
    /**
     * Regular user role with limited access permissions.
     */
    @Schema(description = "Обычный пользователь с ограниченными правами доступа.")
    USER("USER"),

    /**
     * Admin role with elevated access permissions.
     */
    @Schema(description = "Администратор с расширенными правами доступа.")
    ADMIN("ADMIN");

    /**
     * The string value associated with the role.
     */
    @Schema(description = "Строковое значение, представляющее роль.", example = "USER")
    public final String value;

    /**
     * Constructor for the {@link Role} enum.
     *
     * @param value the string value representing the role
     */
    Role(String value) {
        this.value = value;
    }
}
