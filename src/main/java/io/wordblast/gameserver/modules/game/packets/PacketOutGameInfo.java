package io.wordblast.gameserver.modules.game.packets;

import io.wordblast.gameserver.modules.game.GameStatus;
import io.wordblast.gameserver.modules.game.PlayerInfo;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Packet representing a game info message outgoing to game socket routes.
 */
public final class PacketOutGameInfo extends Packet {
    private final UUID gameUid;
    private final GameStatus status;
    private final Set<PlayerInfo> activePlayerInfos;
    private final UUID ownerUid;
    private final Map<String, String> settings;

    /**
     * Creates a new PacketOutGameInfo instance.
     * 
     * @param gameUid the unique identifier of the game.
     * @param status the status of the game.
     * @param activePlayerInfos the usernames of the active players inside the game.
     * @param ownerUid the unique identifier of the owner user of the game.
     * @param settings the settings of the game.
     */
    public PacketOutGameInfo(UUID gameUid, GameStatus status, Set<PlayerInfo> activePlayerInfos,
        UUID ownerUid, Map<String, String> settings) {
        super(PacketType.PACKET_OUT_GAME_INFO);
        this.gameUid = gameUid;
        this.status = status;
        this.activePlayerInfos = activePlayerInfos;
        this.ownerUid = ownerUid;
        this.settings = settings;
    }

    public UUID getGameUid() {
        return gameUid;
    }

    public GameStatus getStatus() {
        return status;
    }

    public Set<PlayerInfo> getActivePlayerInfos() {
        return activePlayerInfos;
    }

    public UUID getOwnerUid() {
        return ownerUid;
    }

    public Map<String, String> getSettings() {
        return settings;
    }
}
