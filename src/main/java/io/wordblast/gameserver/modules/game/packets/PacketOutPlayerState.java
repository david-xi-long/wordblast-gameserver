package io.wordblast.gameserver.modules.game.packets;

/**
 * Packet sent out when a player's state changes within a game.
 */
public class PacketOutPlayerState extends Packet {
    private final String username;
    private final boolean state;

    /**
     * Create a new PacketOutPlayerState instance.
     * 
     * @param username the username of the player whose state has changed.
     * @param state the new state of the player.
     */
    public PacketOutPlayerState(String username, boolean state) {
        super(PacketType.PACKET_OUT_PLAYER_STATE);
        this.username = username;
        this.state = state;
    }

    public String getUsername() {
        return username;
    }

    public boolean getState() {
        return state;
    }
}
