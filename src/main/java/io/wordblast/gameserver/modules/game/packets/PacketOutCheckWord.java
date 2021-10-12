package io.wordblast.gameserver.modules.game.packets;

/**
 * Represents an outgoing packet for the PacketOutCheckWord request.
 */
public class PacketOutCheckWord extends Packet {

     private final boolean valid;

     /**
      * Creates a new PacketOutCheckWord instance.
      * @param valid whether the word is valid in the game.
      */
     public PacketOutCheckWord(boolean valid) {
         super(PacketType.PACKET_OUT_CHECK_WORD);
         this.valid = valid;
     }

     public boolean wordIsValid() {
         return valid;
     }
}
