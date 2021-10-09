package io.wordblast.gameserver.modules.authentication;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * The data transfer object for users.
 */
@PasswordMatch
public class UserDto {
    @NotNull
    @NotEmpty
    @Email(message = "Email must be in a valid format.")
    private String email;
    @NotNull
    @NotEmpty
    @Size(min = 8, message = "Password must contain 8 or more characters.")
    private String password;
    private String matchingPassword;

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }
}
