package io.wordblast.gameserver.modules.game.packets;

/**
 * A packet which represents some outgoing exception.
 */
public class PacketOutException extends Packet {
    private final String message;

    /**
     * Creates a new PacketOutException instance.
     * 
     * @param message the message of the exception.
     */
    public PacketOutException(String message) {
        super(PacketType.PACKET_OUT_EXCEPTION);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
