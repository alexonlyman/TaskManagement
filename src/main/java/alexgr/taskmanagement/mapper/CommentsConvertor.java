package alexgr.taskmanagement.mapper;


import alexgr.taskmanagement.dto.comment.Comment;
import alexgr.taskmanagement.entity.CommentEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
/**
 * Component class responsible for converting between Comment DTOs and CommentEntity entities.
 * <p>
 * This class uses ModelMapper to map fields from a Comment DTO to a CommentEntity entity.
 * It provides a method to convert a Comment object to a CommentEntity object, facilitating
 * the transformation of data between different layers of the application.
 * </p>
 */
@Component
public class CommentsConvertor {
    /**
     * ModelMapper instance used for object mapping.
     */
    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Converts a Comment DTO to a CommentEntity entity.
     * <p>
     * This method configures the ModelMapper and maps the fields from the Comment DTO
     * to the CommentEntity entity. If the input Comment is null, the method returns null.
     * </p>
     *
     * @param comment The Comment DTO to be converted.
     * @return The converted CommentEntity entity, or null if the input is null.
     */
    public CommentEntity convertToEntity(Comment comment) {
        configureModelMapper();
        if (comment == null) {
            return null;
        }
        return modelMapper.map(comment, CommentEntity.class);
    }

    /**
     * Configures the ModelMapper with a loose matching strategy.
     * <p>
     * This method sets the matching strategy of the ModelMapper to LOOSE, allowing
     * for more flexible field matching during the mapping process.
     * </p>
     */
    private void configureModelMapper() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
    }
}
