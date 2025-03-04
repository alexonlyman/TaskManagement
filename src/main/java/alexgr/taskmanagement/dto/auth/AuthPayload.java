package alexgr.taskmanagement.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Data Transfer Object (DTO) for authentication and registration payloads.
 * <p>
 * This class contains the user's email and password, which are validated to ensure proper formatting
 * and length constraints.
 * </p>
 */
@Data
@Schema(description = "Payload для аутентификации пользователя, содержащий email и пароль.")
public class AuthPayload {

    /**
     * The user's email address.
     * <p>
     * Must not be empty and should follow a valid email format.
     * </p>
     */
    @Schema(description = "Адрес электронной почты пользователя.",
            example = "user@example.com",
            required = true)
    @NotEmpty(message = "Поле не может быть пустым")
    @Email
    private String email;

    /**
     * The user's password.
     * <p>
     * Must not be empty and should have a length between 8 and 16 characters.
     * </p>
     */
    @Schema(description = "Пароль пользователя. Должен быть длиной от 8 до 16 символов.",
            example = "secureP@ssword123",
            required = true)
    @NotEmpty(message = "Поле не может быть пустым")
    @Size(min = 8, max = 16, message = "Пароль не может быть меньше 8 и больше 16 символов")
    private String password;

}
