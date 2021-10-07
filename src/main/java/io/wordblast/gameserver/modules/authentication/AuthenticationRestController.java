package io.wordblast.gameserver.modules.authentication;

import java.util.concurrent.CompletableFuture;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * The authentication REST endpoints.
 */
@RestController
public class AuthenticationRestController {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    /**
     * This endpoint will attempt to create a user account.
     * 
     * @return the available game.
     */
    @PostMapping("/api/user")
    public CompletableFuture<String> createUserAccount(@RequestBody @Valid UserDto userDto) {
        return userDetailsService.registerUser(userDto)
            .thenApply((newUser) -> {
                return "Successfully created user. UID: " + newUser.getUid();
            });
    }

    /**
     * Handles exceptions thrown inside request handlers.
     * 
     * @param ex the exception to handle.
     * @return the result of the handled exception.
     */
    @ExceptionHandler()
    public ResponseEntity<String> handleExceptions(Exception ex) throws ResponseStatusException {
        String errMessage = String.format("{\"error\": \"%s\"}", ex.getMessage());

        if (ex instanceof UserAlreadyExistsException) {
            return new ResponseEntity<>(errMessage, HttpStatus.CONFLICT);
        } else if (ex instanceof MethodArgumentNotValidException) {
            return new ResponseEntity<>(errMessage, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(errMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
