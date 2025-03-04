package alexgr.taskmanagement.repository;

import alexgr.taskmanagement.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing {@link CommentEntity} entities.
 *
 * <p>Extends {@link JpaRepository} to provide CRUD operations and query methods for the {@link CommentEntity}.</p>
 *
 * @see JpaRepository
 */
public interface CommentsRepo extends JpaRepository<CommentEntity, Integer> {

}
