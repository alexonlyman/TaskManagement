package alexgr.taskmanagement.mapper;

import alexgr.taskmanagement.dto.user.UpdateUser;
import alexgr.taskmanagement.dto.user.User;
import alexgr.taskmanagement.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
/**
 * A converter class for transforming objects between User, UserEntity, and UpdateUser.
 * Uses ModelMapper for mapping properties between objects.
 */
@Component
@RequiredArgsConstructor
public class UserConvertor {
    /**
     * ModelMapper instance for object mapping.
     * The mapping strategy is configured to LOOSE matching.
     */
    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Converts a UserEntity object to a User object.
     *
     * @param userEntity the UserEntity object to be converted.
     *                   If the input is null, the method returns null.
     * @return the converted User object, or null if the input is null.
     */
    public User convertToDto(UserEntity userEntity) {
        configureModelMapper();
        if (userEntity == null) {
            return null;
        }
        return modelMapper.map(userEntity, User.class);
    }

    /**
     * Converts an UpdateUser object to a UserEntity object.
     *
     * @param updateUser the UpdateUser object to be converted.
     *                   If the input is null, the method returns null.
     * @return the converted UserEntity object, or null if the input is null.
     */
    public UserEntity updateConvertor(UpdateUser updateUser) {
        configureModelMapper();
        if (updateUser == null) {
            return null;
        }
        return modelMapper.map(updateUser, UserEntity.class);
    }

    /**
     * Converts a generic Object to a UserEntity object.
     *
     * @param user the object to be converted.
     *             If the input is null, the method returns null.
     * @return the converted UserEntity object, or null if the input is null.
     */
    public UserEntity convertToEntity(Object user) {
        configureModelMapper();
        if (user == null) {
            return null;
        }
        return modelMapper.map(user, UserEntity.class);
    }

    /**
     * Configures the ModelMapper instance with a LOOSE matching strategy.
     */
    private void configureModelMapper() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
    }
}
