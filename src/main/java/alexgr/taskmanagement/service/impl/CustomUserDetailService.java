package alexgr.taskmanagement.service.impl;

import alexgr.taskmanagement.entity.UserEntity;
import alexgr.taskmanagement.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Custom implementation of the {@link UserDetailsService} interface.
 *
 * <p>This service is responsible for loading user details for authentication purposes.</p>
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepo userRepo;

    /**
     * Loads a user by their email address.
     *
     * @param email the email address of the user to load.
     * @return a {@link UserDetails} object containing user information and authorities.
     * @throws UsernameNotFoundException if the user with the specified email is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepo.findUserByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), getAuthorities(user));


    }

    /**
     * Retrieves the authorities (roles) granted to the user.
     *
     * @param user the {@link UserEntity} whose authorities are to be retrieved.
     * @return a collection of {@link GrantedAuthority} objects representing the user's roles.
     */
    private Collection<? extends GrantedAuthority> getAuthorities(UserEntity user) {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
    }
}
