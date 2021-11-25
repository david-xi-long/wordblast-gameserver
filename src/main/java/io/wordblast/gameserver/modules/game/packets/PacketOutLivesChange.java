package io.wordblast.gameserver.modules.game.packets;

/**
 * Packet which is sent when a player's lives have changed.
 */
public class PacketOutLivesChange extends Packet {
    private final String username;
    private final int lives;

    /**
     * Creates a new PacketOutLivesChange packet.
     * 
     * @param username the username of the player whose lives changed.
     * @param lives the new lives of the player.
     */
    public PacketOutLivesChange(String username, int lives) {
        super(PacketType.PACKET_OUT_LIVES_CHANGE);
        this.username = username;
        this.lives = lives;
    }

    public String getUsername() {
        return username;
    }

    public int getLives() {
        return lives;
    }
}
