package io.wordblast.gameserver.modules.authentication;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * The implementation of the user details interface.
 */
public class UserDetailsImpl implements UserDetails {
    private UUID uid;
    private String username;
    private String password;

    /**
     * Creates a new instance of the user details class.
     * 
     * @param uid the unique identifier of the user account.
     * @param username the username (or email) of the user account.
     * @param password the hashed password of the user account.
     */
    public UserDetailsImpl(UUID uid, String username, String password) {
        this.uid = uid;
        this.username = username;
        this.password = password;
    }

    public UUID getUid() {
        return uid;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
