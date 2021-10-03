package io.wordblast.gameserver.modules.game.packets;

import java.util.Set;
import java.util.UUID;

/**
 * Packet representing a game info message outgoing to game socket routes.
 */
public final class PacketOutGameInfo extends Packet {
    private final UUID gameUid;
    private final Set<UUID> playerUids;

    /**
     * Creates a new PacketOutGameInfo instance.
     * 
     * @param gameUid the unique identifier of the game.
     * @param playerUids the unique identifiers of the players inside the game.
     */
    public PacketOutGameInfo(UUID gameUid, Set<UUID> playerUids) {
        super(PacketType.PACKET_OUT_GAME_INFO);
        this.gameUid = gameUid;
        this.playerUids = playerUids;
    }

    public UUID getGameUid() {
        return gameUid;
    }

    public Set<UUID> getPlayerUids() {
        return playerUids;
    }
}
