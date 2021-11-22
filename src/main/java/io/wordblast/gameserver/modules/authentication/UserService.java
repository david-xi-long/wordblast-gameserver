package io.wordblast.gameserver.modules.authentication;

import io.wordblast.gameserver.modules.database.UserRepository;
import io.wordblast.gameserver.modules.game.Player;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * The implementation of the user service.
 */
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Attempts to register a user.
     * 
     * @param userDto the user data transfer object to extract data from.
     * @return the registered user, if successful.
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
    
    /**
     * Attempts to update a user.
     * 
     * @param Player the player in a game to update.
     * @return the updated user, if successful.
     */
    public CompletableFuture<User> updateUser(Player player) {
        return userRepository.findById(player.getEmail())
            .toFuture()
            .thenCompose((userFound) -> {
                if (userFound == null) {
                    return CompletableFuture.failedFuture(new UserDoesNotExistException());
                }

                userFound.setGamesPlayed(userFound.getGamesPlayed() + 1);

                return userRepository.save(userFound)
                    .toFuture();
            });
    }
}
