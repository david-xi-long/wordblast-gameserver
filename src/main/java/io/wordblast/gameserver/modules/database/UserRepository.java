package io.wordblast.gameserver.modules.database;

import io.wordblast.gameserver.modules.authentication.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * Interface describing the user repository.
 */
@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {
    Mono<User> findByEmail(String email);

    Mono<User> findByIdAsString(String uid);
}
