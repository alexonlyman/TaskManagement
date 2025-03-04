package alexgr.taskmanagement.repository;

import alexgr.taskmanagement.dto.role.Role;
import alexgr.taskmanagement.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
/**
 * Repository interface for managing {@link RoleEntity} entities.
 *
 * <p>Extends {@link JpaRepository} to provide CRUD operations and query methods for the {@link RoleEntity}.</p>
 *
 * @see JpaRepository
 */
public interface RoleRepo extends JpaRepository<RoleEntity, Integer> {

    /**
     * Finds a {@link RoleEntity} by the specified {@link Role}.
     *
     * @param role the {@link Role} to search for.
     * @return an {@link Optional} containing the found {@link RoleEntity}, or empty if no entity is found.
     */
    Optional<RoleEntity> findByRole(Role role);

}
