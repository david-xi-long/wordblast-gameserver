package io.wordblast.gameserver.modules.game;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * The game manager will store the active games of the game server.
 */
public final class GameManager {
    /**
     * The active games of the game server.
     */
    private static final Map<UUID, Game> GAMES = new HashMap<>();

    /**
     * Class cannot be instantiated.
     */
    private GameManager() {
        throw new IllegalStateException("Class cannot be instantiated.");
    }

    /**
     * Register a game with the game manager.
     * 
     * @param game the game to register.
     */
    public static void registerGame(final Game game) {
        GAMES.put(game.getUid(), game);
    }

    /**
     * Unregister a game from the game manager.
     *
     * @param game the game to unregister.
     */
    public static void unregisterGame(final Game game) {
        GAMES.remove(game.getUid());
    }

    /**
     * Searches for the game with the associated unique identifier.
     *
     * @param gameUid the unique identifier of the game to search for.
     * @return the retrieved game if found, otherwise {@code null}.
     */
    public static Game getGameFromUid(final UUID gameUid) {
        return GAMES.get(gameUid);
    }

    /**
     * Searches for the game with the associated short identifier.
     * 
     * @param gameSid the short identifier of the game to search for.
     * @return the retrieved game if found, otherwise {@code null}.
     */
    public static Game getGameFromSid(final String gameSid) {
        return GAMES.values()
            .stream()
            .filter((game) -> game.getSid().equals(gameSid))
            .findFirst()
            .orElse(null);
    }

    /**
     * Retrieves the active games of the game manager.
     * 
     * @return the games.
     */
    public static Collection<Game> getGames() {
        return GAMES.values();
    }
}
