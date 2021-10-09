package io.wordblast.gameserver.modules.authentication;

import java.util.concurrent.CompletableFuture;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.WebExchangeBindException;

/**
 * The authentication REST endpoints.
 */
@RestController
public class AuthenticationRestController {
    @Autowired
    private UserService userService;

    /**
     * This endpoint will attempt to create a user account.
     * 
     * @return the created user account.
     */
    @PostMapping(path = "/api/user", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public CompletableFuture<String> createUserAccount(@Valid UserDto userDto) {
        return userService.registerUser(userDto)
            .thenApply((newUser) -> String.format("{\"uid\": \"%s\"}", newUser.getUid()));
    }

    /**
     * This endpoint will retrieve the logged in user account of the session.
     * 
     * @return the logged in user account.
     */
    @GetMapping("/api/user")
    public CompletableFuture<String> getUserAccount(@AuthenticationPrincipal UserDetailsImpl user) {
        return CompletableFuture.completedFuture(
            String.format("{\"authenticated\": %b, \"uid\": \"%s\"}", user != null,
                user != null ? user.getUid() : null));
    }

    /**
     * Handles exceptions thrown inside request handlers.
     * 
     * @param ex the exception to handle.
     * @return the result of the handled exception.
     */
    @ExceptionHandler
    public ResponseEntity<String> handleExceptions(Exception ex) {
        HttpStatus status;
        String exMessage;

        if (ex instanceof UserAlreadyExistsException) {
            status = HttpStatus.CONFLICT;
            exMessage = ex.getMessage();
        } else if (ex instanceof UserNotFoundException) {
            status = HttpStatus.BAD_REQUEST;
            exMessage = ex.getMessage();
        } else if (ex instanceof WebExchangeBindException) {
            WebExchangeBindException bindEx = (WebExchangeBindException) ex;

            status = HttpStatus.BAD_REQUEST;
            exMessage = bindEx.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            exMessage = ex.getMessage();
        }

        String errMessage = String.format("{\"error\": \"%s\"}", exMessage);

        return new ResponseEntity<>(errMessage, status);
    }
}
