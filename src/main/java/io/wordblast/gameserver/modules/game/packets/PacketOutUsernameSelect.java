package io.wordblast.gameserver.modules.game.packets;

/**
 * Reprensents an outgoing packet for the PacketInUsernameSelect request.
 */
public class PacketOutUsernameSelect extends Packet {
    private final boolean usernameExists;

    /**
     * Creates a new PacketOutUsernameSelect instance.
     * 
     * @param usernameExists whether the username is already taken in the game.
     */
    public PacketOutUsernameSelect(boolean usernameExists) {
        super(PacketType.PACKET_OUT_USERNAME_SELECT);
        this.usernameExists = usernameExists;
    }

    public boolean getUsernameExists() {
        return usernameExists;
    }
}
