package io.wordblast.gameserver.modules.authentication;

import io.wordblast.gameserver.modules.database.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * The implementation of a user details service.
 */
@Service
public class ReactiveUserDetailsServiceImpl implements ReactiveUserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Mono<UserDetails> findByUsername(String email) {
        return userRepository.findByEmail(email)
            .map((user) -> new UserDetailsImpl(user.getUid(), user.getEmail(),
                user.getHashedPassword()));
    }
}
