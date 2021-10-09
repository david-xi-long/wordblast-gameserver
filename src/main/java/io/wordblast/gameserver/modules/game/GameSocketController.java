package io.wordblast.gameserver.modules.game;

import io.wordblast.gameserver.modules.game.packets.PacketInGameJoin;
import io.wordblast.gameserver.modules.game.packets.PacketInSelectUsername;
import io.wordblast.gameserver.modules.game.packets.PacketOutException;
import io.wordblast.gameserver.modules.game.packets.PacketOutGameInfo;
import io.wordblast.gameserver.modules.game.packets.PacketOutSelectUsername;
import java.util.Set;
import java.util.UUID;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

/**
 * The game socket controller.
 */
@Controller
public class GameSocketController {
    /**
     * Handles incoming messages of the game join route.
     *
     * @param packet the join game packet sent from the client.
     * @return the game info packet sent to the client.
     */
    @MessageMapping("join")
    public Mono<PacketOutGameInfo> joinGame(PacketInGameJoin packet) {
        UUID gameUid = packet.getGameUid();
        UUID playerUid = packet.getPlayerUid();

        System.out.println("Game UID: " + gameUid + ", Player UID: " + playerUid);

        return Mono.just(new PacketOutGameInfo(gameUid, Set.of(playerUid)));
    }

    /**
     * Handles players select their username within a game.
     * 
     * @param packet the packet to handle.
     * @return the packet response.
     */
    @MessageMapping("select-username")
    public Mono<PacketOutSelectUsername> selectUsername(PacketInSelectUsername packet) {
        UUID gameUid = packet.getGameUid();
        String username = packet.getUsername();

        Game game = GameManager.getGame(gameUid);

        if (game == null) {
            return Mono.error(new GameNotFoundException());
        }

        boolean usernameExists = game.getPlayers()
            .stream()
            .anyMatch((p) -> p.getUsername().equalsIgnoreCase(username));

        return Mono.just(new PacketOutSelectUsername(usernameExists));
    }

    /**
     * Handles exceptions from the game socket.
     * 
     * @param exception the exception to andle.
     * @return the result of the handled exception.
     */
    @MessageExceptionHandler
    public Mono<PacketOutException> handleException(Exception exception) {
        return Mono.just(new PacketOutException(exception.getMessage()));
    }
}
