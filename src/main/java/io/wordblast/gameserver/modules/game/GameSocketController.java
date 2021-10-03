package io.wordblast.gameserver.modules.game;

import io.wordblast.gameserver.modules.game.packets.PacketInGameJoin;
import io.wordblast.gameserver.modules.game.packets.PacketOutGameInfo;
import java.util.Set;
import java.util.UUID;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * The game socket controller.
 */
@Controller
public class GameSocketController {
    /**
     * Handles incoming messages of the game join route.
     * 
     * @param joinGamePacket the join game packet sent from the client.
     * @return the game info packet sent to the client.
     */
    @MessageMapping("/join")
    @SendTo("/game/info")
    public PacketOutGameInfo joinGame(PacketInGameJoin joinGamePacket) {
        UUID gameUid = joinGamePacket.getGameUid();
        UUID playerUid = joinGamePacket.getPlayerUid();

        System.out.println("Game UID: " + gameUid + ", Player UID: " + playerUid);

        return new PacketOutGameInfo(gameUid, Set.of(playerUid));
    }
}
