package io.wordblast.gameserver.modules.game.packets;

/**
 * A packet which is sent out when a player is eliminated from a game.
 */
public class PacketOutPlayerEliminated extends Packet implements PlayerPacket {
    private final String username;

    /**
     * Creates a new PacketOutPlayerEliminated instance.
     * 
     * @param username the username of the eliminated player.
     */
    public PacketOutPlayerEliminated(String username) {
        super(PacketType.PACKET_OUT_PLAYER_ELIMINATED);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
