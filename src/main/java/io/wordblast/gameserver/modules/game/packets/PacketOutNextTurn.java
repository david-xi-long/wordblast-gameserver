package io.wordblast.gameserver.modules.game.packets;

public class PacketOutNextTurn extends Packet {
    private final String letterCombo;
    private final int timeToAnswer;

    public PacketOutNextTurn(String letterCombo, int timeToAnswer) {
        super(PacketType.PACKET_OUT_NEXT_TURN);
        this.letterCombo = letterCombo;
        this.timeToAnswer = timeToAnswer;
    }
    
    public String getLetterCombo() {
        return letterCombo;
    }

    public int getTimeToAnswer() {
        return timeToAnswer;
    }
}


