package io.wordblast.gameserver.modules.game.packets;

import java.util.UUID;

/**
 * Packet representing a join game message incoming from game socket routes.
 */
public final class PacketInGameJoin extends PacketInGame {
    /**
     * Creates a new PacketInJoinGame instance.
     * 
     * @param gameUid the unique identifier of the game to join.
     * @param playerUid the unique identifier of the joining player.
     */
    public PacketInGameJoin(UUID gameUid, UUID playerUid) {
        super(PacketType.PACKET_IN_GAME_JOIN, gameUid, playerUid);
    }
}
