package io.wordblast.gameserver.modules.game.packets;

import java.util.UUID;

/**
 * A packet which is sent out to inform clients about the current progress of a game.
 */
public class PacketOutRoundInfo extends Packet {
    private final UUID gameUid;
    private final int round;
    private final String player;
    private final long timeRemaining;

    /**
     * Creates a new PacketOutRoundInfo instance.
     * 
     * @param gameUid the unique identifier of the game.
     * @param round the current round of the game.
     * @param player the current player of the game.
     * @param timeRemaining the time remaining for the current player.
     */
    public PacketOutRoundInfo(UUID gameUid, int round, String player, long timeRemaining) {
        super(PacketType.PACKET_OUT_ROUND_INFO);
        this.gameUid = gameUid;
        this.round = round;
        this.player = player;
        this.timeRemaining = timeRemaining;
    }

    public UUID getGameUid() {
        return gameUid;
    }

    public int getRound() {
        return round;
    }

    public String getPlayer() {
        return player;
    }

    public long getTimeRemaining() {
        return timeRemaining;
    }
}
