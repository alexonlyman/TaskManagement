package alexgr.taskmanagement.repository;

import alexgr.taskmanagement.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * Repository interface for managing {@link UserEntity} entities.
 *
 * <p>Extends {@link JpaRepository} to provide CRUD operations and custom query methods for {@link UserEntity}.</p>
 *
 * @see JpaRepository
 */
public interface UserRepo extends JpaRepository<UserEntity, Integer> {
    /**
     * Finds a {@link UserEntity} by its unique identifier.
     *
     * @param id the unique identifier of the user.
     * @return the {@link UserEntity} with the specified ID, or {@code null} if no user is found.
     */
    UserEntity findUserById(Integer id);

    /**
     * Finds a {@link UserEntity} by its email address.
     *
     * @param email the email address of the user.
     * @return the {@link UserEntity} with the specified email, or {@code null} if no user is found.
     */
    UserEntity findUserByEmail(String email);

}
