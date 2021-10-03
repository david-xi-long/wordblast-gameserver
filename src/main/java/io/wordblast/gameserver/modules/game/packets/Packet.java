package io.wordblast.gameserver.modules.game.packets;

/**
 * A generic message sent through game socket routes. See
 * {@link io.wordblast.gameserver.modules.game.packets.PacketInGameJoin PacketInJoinGame} or
 * {@link io.wordblast.gameserver.modules.game.packets.PacketOutGameInfo PacketOutGameInfo} for
 * example implementations.
 */
public abstract class Packet {
    private final PacketType type;

    public Packet(PacketType type) {
        this.type = type;
    }

    public PacketType getType() {
        return type;
    }
}
