package io.wordblast.gameserver.modules.game.packets;

/**
 * Reprensents an outgoing packet for the PacketInSelectUsername request.
 */
public class PacketOutSelectUsername extends Packet {
    private final boolean exists;

    /**
     * Creates a new PacketInSelectUsername instance.
     * 
     * @param exists whether the username exists in the game.
     */
    public PacketOutSelectUsername(boolean exists) {
        super(PacketType.PACKET_OUT_SELECT_USERNAME);
        this.exists = exists;
    }

    public boolean usernameExists() {
        return exists;
    }
}
