package io.wordblast.gameserver.modules.game.packets;

import io.wordblast.gameserver.modules.game.PlayerInfo;

/**
 * This packet is sent out when a player joins a game.
 */
public class PacketOutPlayerJoin extends Packet {
    private final PlayerInfo player;

    public PacketOutPlayerJoin(PlayerInfo player) {
        super(PacketType.PACKET_OUT_PLAYER_JOIN);
        this.player = player;
    }

    public PlayerInfo getPlayer() {
        return this.player;
    }
}
