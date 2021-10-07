package io.wordblast.gameserver.modules.authentication;

import io.wordblast.gameserver.modules.database.UserRepository;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * The implementation of a user details service.
 */
@Service
public class UserDetailsServiceImpl {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Attempts to register a user.
     * 
     * @param userDto the user data transfer object to extract data from.
     * @return the registered user, if successful.
     * @throws UserAlreadyExistsException thrown if the user already has an existing account.
     */
    public CompletableFuture<User> registerUser(UserDto userDto) {
        return userRepository.findById(userDto.getEmail())
            .toFuture()
            .thenCompose((userFound) -> {
                if (userFound != null) {
                    return CompletableFuture.failedFuture(new UserAlreadyExistsException());
                }

                User newUser = new User();
                newUser.setEmail(userDto.getEmail());
                newUser.setHashedPassword(passwordEncoder.encode(userDto.getPassword()));

                return userRepository.insert(newUser)
                    .toFuture();
            });
    }
}
