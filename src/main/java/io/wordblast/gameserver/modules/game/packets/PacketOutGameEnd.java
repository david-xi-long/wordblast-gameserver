package io.wordblast.gameserver.modules.game.packets;

/**
 * A packet sent out when a game ends.
 */
public class PacketOutGameEnd extends Packet {
    /**
     * Creates a new PacketOutGameEnd instance.
     */
    public PacketOutGameEnd() {
        super(PacketType.PACKET_OUT_GAME_END);
    }
}
