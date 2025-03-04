package alexgr.taskmanagement.service;



import alexgr.taskmanagement.dto.user.UpdateUser;
import alexgr.taskmanagement.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Service interface for managing user-related operations.
 */
public interface UserService {

    /**
     * Updates the information of the current user.
     *
     * @param updateUser the DTO containing the updated user information.
     * @return the updated user information as an {@link UpdateUser} object.
     */
    UpdateUser updateUserInfo(UpdateUser updateUser);

    /**
     * Retrieves a list of all user email addresses.
     *
     * @return a list of email addresses for all registered users.
     */
    List<String> getAllUsers();

    /**
     * Retrieves the email of the currently authenticated user.
     *
     * @return the email of the current user.
     */
    String getCurrentUser();
}

