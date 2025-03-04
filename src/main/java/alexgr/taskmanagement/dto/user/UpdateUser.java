package alexgr.taskmanagement.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
/**
 * Data Transfer Object (DTO) for updating user information.
 * <p>
 * This class encapsulates the details that can be updated for a user, including
 * the first name, last name, and email address. It is used to transfer updated
 * user data between different layers of an application, such as from the client
 * to the server.
 * </p>
 */
@Data
@Schema(description = "Модель для обновления информации о пользователе.")
public class UpdateUser {
    /**
     * The user's first name.
     * <p>
     * Represents the updated first name of the user.
     * </p>
     */
    @Schema(description = "Имя пользователя. Представляет обновленное имя.", example = "John")
    private String firstName;

    /**
     * The user's last name.
     * <p>
     * Represents the updated last name of the user.
     * </p>
     */
    @Schema(description = "Фамилия пользователя. Представляет обновленную фамилию.", example = "Doe")
    private String lastName;

    /**
     * The user's email address.
     * <p>
     * Represents the updated email address of the user. Must follow a valid email format.
     * </p>
     */
    @Schema(description = "Адрес электронной почты пользователя. Должен быть валидным email.", example = "john.doe@example.com")
    private String email;
}
