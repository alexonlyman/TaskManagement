package alexgr.taskmanagement.service.impl;


import alexgr.taskmanagement.dto.user.UpdateUser;
import alexgr.taskmanagement.entity.UserEntity;
import alexgr.taskmanagement.exceptions.UserEmailNotFoundException;
import alexgr.taskmanagement.repository.UserRepo;
import alexgr.taskmanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
/**
 * class for working with users
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    /**
     * Updates user information based on the provided data.
     *
     * @param updateUser DTO containing updated user information.
     * @return Updated user information.
     * @throws UserEmailNotFoundException if the user is not found by the provided email.
     */
    @Transactional
    @Override
    public UpdateUser updateUserInfo(UpdateUser updateUser) {
        String email = updateUser.getEmail();
        UserEntity user = userRepo.findUserByEmail(email);

        if (user == null) {
            logger.warn("User not found for email: {}", email);
            throw new UserEmailNotFoundException("Email not found: " + email);
        }

        user.setFirstName(updateUser.getFirstName());
        user.setLastName(updateUser.getLastName());
        userRepo.save(user);

        logger.info("User information updated successfully for email: {}", email);
        return updateUser;
    }

    /**
     * Retrieves all users from the repository.
     *
     * @return List of all users.
     */
    @Override
    public List<String> getAllUsers() {
        List<UserEntity> list = userRepo.findAll();
        return list.stream().map(UserEntity::getEmail).collect(Collectors.toList());
    }


    /**
     * Retrieves the first names of all users.
     *
     * @return List of first names of all users.
     */
    public List<String> getNamesOfAllUsers() {
        return userRepo.findAll()
                .stream()
                .map(UserEntity::getEmail)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves the username of the currently authenticated user.
     *
     * @return Username of the authenticated user.
     */
    @Override
    public String getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetails userDetails)) {
            logger.warn("No authenticated user found.");
            return null;
        }

        return userDetails.getUsername();
    }


}
