package io.wordblast.gameserver.modules.authentication;

import io.wordblast.gameserver.modules.database.UserRepository;
import io.wordblast.gameserver.modules.game.Player;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

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
        return userRepository.findByEmail(userDto.getEmail())
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
     * Atempts to retrieve the user from with the given unique identifier from the database.
     * 
     * @param userUid the unique identifier of the player to retrieve.
     * @return the retrieved user, if found. Otherwise, {@code null}.
     */
    public Mono<User> getUser(UUID userUid) {
        return userRepository.findByIdAsString(userUid.toString());
    }

    /**
     * Attempts to update a user.
     * 
     * @param player the player in a game to update.
     * @return the updated user, if successful.
     */
    public CompletableFuture<User> updateUser(Player player) {
        return userRepository.findByIdAsString(player.getUid().toString())
            .toFuture()
            .thenCompose((user) -> {
                if (user == null) {
                    return CompletableFuture.completedStage(null);
                }

                // Update WPM, level, total words, total games played, and experience
                user.setGamesPlayed(user.getGamesPlayed() + 1);
                user.setTotalWords(user.getTotalWords() + player.getUsedWords().size());
                user.addExperience(player.getExperience());

                return userRepository.save(user)
                    .toFuture();
            });
    }
}
