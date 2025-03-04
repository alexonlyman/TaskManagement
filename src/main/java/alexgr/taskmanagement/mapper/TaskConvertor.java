package alexgr.taskmanagement.mapper;


import alexgr.taskmanagement.dto.task.Task;
import alexgr.taskmanagement.entity.TaskEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
/**
 * A utility component for converting between {@link TaskEntity} and {@link Task} objects.
 * <p>
 * This class uses {@link ModelMapper} to handle the mapping of properties between the entity and DTO.
 * </p>
 */
@Component
public class TaskConvertor {
    /**
     * ModelMapper instance used for object mapping.
     */
    private final ModelMapper modelMapper = new ModelMapper();


    /**
     * Converts a {@link TaskEntity} object to a {@link Task} DTO.
     *
     * @param taskEntity the {@link TaskEntity} object to convert
     * @return the converted {@link Task} DTO, or {@code null} if the input is {@code null}
     */
    public Task convertToDto(TaskEntity taskEntity) {
        configureModelMapper();
        if (taskEntity == null) {
            return null;
        }
        return modelMapper.map(taskEntity, Task.class);
    }

    /**
     * Converts a {@link Task} DTO to a {@link TaskEntity}.
     *
     * @param task the {@link Task} DTO to convert
     * @return the converted {@link TaskEntity}, or {@code null} if the input is {@code null}
     */
    public TaskEntity convertToEntity(Task task) {
        configureModelMapper();
        if (task == null) {
            return null;
        }
        return modelMapper.map(task, TaskEntity.class);
    }

    /**
     * Configures the {@link ModelMapper} with a loose matching strategy for flexible property mapping.
     */
    private void configureModelMapper() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
    }
}
