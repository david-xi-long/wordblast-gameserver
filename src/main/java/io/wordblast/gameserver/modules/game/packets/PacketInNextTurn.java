package io.wordblast.gameserver.modules.game.packets;

import java.util.UUID;

public class PacketInNextTurn extends Packet{
    private final UUID gameUid;
    private final boolean outOfTime;
    
    public PacketInNextTurn(UUID gameUid, boolean outOfTime) {
        super(PacketType.PACKET_IN_NEXT_TURN);
        this.gameUid = gameUid;
        this.outOfTime = outOfTime;
    }

    public UUID getGameUid() {
        return gameUid;
    }

    public boolean getOutOfTime() {
        return outOfTime;
    }
    
}
