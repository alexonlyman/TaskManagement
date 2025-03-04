package alexgr.taskmanagement.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
/**
 * Represents a user in the application.
 * <p>
 * This class contains basic user information such as first name, last name, email, and password.
 * </p>
 */
@Data
@Schema(description = "Модель пользователя, содержащая основную информацию о пользователе, включая учетные данные.")
public class User {
    /**
     * The first name of the user.
     */
    @Schema(description = "Имя пользователя.", example = "John")
    private String firstName;

    /**
     * The last name of the user.
     */
    @Schema(description = "Фамилия пользователя.", example = "Doe")
    private String lastName;

    /**
     * The email address of the user.
     * <p>
     * This serves as a unique identifier for the user and is used for authentication.
     * </p>
     */
    @Schema(description = "Электронная почта пользователя. Уникальный идентификатор для аутентификации.", example = "john.doe@example.com")
    private String email;

    /**
     * The password for the user's account.
     * <p>
     * It is stored in an encrypted form for security purposes.
     * </p>
     */
    @Schema(description = "Пароль пользователя. Хранится в зашифрованной форме.", example = "encrypted_password")
    private String password;


}
