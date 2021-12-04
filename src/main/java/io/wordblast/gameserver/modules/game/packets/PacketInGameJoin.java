package io.wordblast.gameserver.modules.game.packets;

import java.util.Map;
import java.util.UUID;

/**
 * Packet representing a join game message incoming from game socket routes.
 */
public final class PacketInGameJoin extends Packet {
    private final UUID gameUid;
    private final String username;
    private final UUID userUid;
    private final Map<String, Object> bigHeadOptions;

    /**
     * Creates a new PacketInGameJoin instance.
     * 
     * @param gameUid the unique identifier of the game.
     * @param username the username of the new player.
     * @param userUid the unique identifier of the new player's user.
     * @param bigHeadOptions the big head options of the new player.
     */
    public PacketInGameJoin(UUID gameUid, String username, UUID userUid,
        Map<String, Object> bigHeadOptions) {
        super(PacketType.PACKET_IN_GAME_JOIN);
        this.gameUid = gameUid;
        this.username = username;
        this.userUid = userUid;
        this.bigHeadOptions = bigHeadOptions;
    }

    public UUID getGameUid() {
        return gameUid;
    }

    public String getUsername() {
        return username;
    }

    public UUID getUserUid() {
        return userUid;
    }

    public Map<String, Object> getBigHeadOptions() {
        return this.bigHeadOptions;
    }
}
