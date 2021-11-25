package io.wordblast.gameserver.modules.game.packets;

import io.wordblast.gameserver.modules.game.PlayerInfo;

/**
 * This packet is sent out when a player quit a game.
 */
public class PacketOutPlayerQuit extends Packet {
    private final PlayerInfo player;

    public PacketOutPlayerQuit(PlayerInfo player) {
        super(PacketType.PACKET_OUT_PLAYER_QUIT);
        this.player = player;
    }

    public PlayerInfo getPlayer() {
        return this.player;
    }
}
