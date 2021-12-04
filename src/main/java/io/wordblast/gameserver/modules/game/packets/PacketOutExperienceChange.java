package io.wordblast.gameserver.modules.game.packets;

/**
 * A packet sent out when a player's experience has changed.
 */
public class PacketOutExperienceChange extends Packet {
    private final String username;
    private final int experienceDelta;

    /**
     * Creates a new PacketOutExperienceChange instance.
     * 
     * @param username the username of player whose experience changed.
     * @param experienceDelta the change in the player's experience.
     */
    public PacketOutExperienceChange(String username, int experienceDelta) {
        super(PacketType.PACKET_OUT_EXPERIENCE_CHANGE);
        this.username = username;
        this.experienceDelta = experienceDelta;
    }

    public String getUsername() {
        return username;
    }

    public int getExperienceDelta() {
        return experienceDelta;
    }
}
