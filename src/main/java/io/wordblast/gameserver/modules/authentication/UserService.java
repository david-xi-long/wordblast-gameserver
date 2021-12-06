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
     * Attempts to update a user.
     * 
     * @param Player the player in a game to update.
     * @return the updated user, if successful.
     */
    public CompletableFuture<User> updateUser(Player player) {
        return userRepository.findByIdAsString(player.getUid().toString())
            .toFuture()
            .thenCompose((userFound) -> {         
                userFound.setGamesPlayed(userFound.getGamesPlayed() + 1);
                userFound.setTotalWords(userFound.getTotalWords() + player.getUsedWords().size());
                userFound.addExperience(player.getXp());
                userFound.setTotalTimeElapsed(userFound.getTotalTimeElapsed() + player.getTimeElapsed());
                userFound.setWPM(userFound.getTotalWords() * 60 / (userFound.getTotalTimeElapsed()));

                // Update most used words and level?

                return userRepository.save(userFound)
                    .toFuture();
            });
    }
}
