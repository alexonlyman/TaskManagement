package alexgr.taskmanagement.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the authentication response containing a generated token.
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    /**
     * The authentication token generated after successful login or registration.
     */
    private String token;
}

