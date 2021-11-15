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
    private final String[] players;
    private final int[] playerLives;
    private final String previousPlayer;
    private final String notificationText;
    private final String letterCombo;

    /**
     * Creates a new PacketOutRoundInfo instance.
     * 
     * @param gameUid the unique identifier of the game.
     * @param round the current round of the game.
     * @param player the current player of the game.
     * @param timeRemaining the time remaining for the current player.
     */
    public PacketOutRoundInfo(UUID gameUid, int round, String player, long timeRemaining,
        String[] players, int[] playerLives, String previousPlayer, String notificationText,
        String letterCombo) {
        super(PacketType.PACKET_OUT_ROUND_INFO);
        this.gameUid = gameUid;
        this.round = round;
        this.player = player;
        this.timeRemaining = timeRemaining;
        this.players = players;
        this.playerLives = playerLives;
        this.previousPlayer = previousPlayer;
        this.notificationText = notificationText;
        this.letterCombo = letterCombo;
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

    public String[] getPlayers() {
        return players;
    }

    public int[] getPlayerLives() {
        return playerLives;
    }

    public String getPreviousPlayer() {
        return previousPlayer;
    }

    public String getNotificationText() {
        return notificationText;
    }

    public String getLetterCombo() {
        return letterCombo;
    }
}
