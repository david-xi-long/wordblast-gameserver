package io.wordblast.gameserver.modules.game.packets;

import io.wordblast.gameserver.modules.game.GameStatus;
import java.util.Set;
import java.util.UUID;

/**
 * Packet representing a game info message outgoing to game socket routes.
 */
public final class PacketOutGameInfo extends Packet {
    private final UUID gameUid;
    private final GameStatus status;
    private final Set<String> activePlayerNames;

    /**
     * Creates a new PacketOutGameInfo instance.
     * 
     * @param gameUid the unique identifier of the game.
     * @param status the status of the game.
     * @param activePlayerNames the usernames of the active players inside the game.
     */
    public PacketOutGameInfo(UUID gameUid, GameStatus status, Set<String> activePlayerNames) {
        super(PacketType.PACKET_OUT_GAME_INFO);
        this.gameUid = gameUid;
        this.status = status;
        this.activePlayerNames = activePlayerNames;
    }

    public UUID getGameUid() {
        return gameUid;
    }

    public GameStatus getStatus() {
        return status;
    }

    public Set<String> getActivePlayerNames() {
        return activePlayerNames;
    }
}
