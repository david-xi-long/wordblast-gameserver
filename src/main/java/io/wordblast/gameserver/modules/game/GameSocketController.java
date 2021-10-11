package io.wordblast.gameserver.modules.game;

import io.wordblast.gameserver.modules.game.packets.PacketInGameJoin;
import io.wordblast.gameserver.modules.game.packets.PacketInSelectUsername;
import io.wordblast.gameserver.modules.game.packets.PacketOutException;
import io.wordblast.gameserver.modules.game.packets.PacketOutGameInfo;
import io.wordblast.gameserver.modules.game.packets.PacketOutPlayerState;
import io.wordblast.gameserver.modules.game.packets.PacketOutSelectUsername;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.rsocket.RSocketRequester;
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
    @MessageMapping("join-game")
    public Mono<PacketOutGameInfo> joinGame(PacketInGameJoin packet, RSocketRequester connection) {
        UUID gameUid = packet.getGameUid();
        String username = packet.getUsername();

        Game game = GameManager.getGame(gameUid);

        if (game == null) {
            return Mono.error(new GameNotFoundException());
        }

        boolean usernameExists = game.getPlayers()
            .stream()
            .anyMatch((p) -> p.getUsername().equalsIgnoreCase(username));

        if (usernameExists) {
            return Mono.error(new UsernameTakenException());
        }

        Player player = new Player(username);
        player.setConnection(connection);
        player.setState(true);

        game.addPlayer(player);

        // Inform clients that the player is active.
        PacketOutPlayerState activeStatePacket =
            new PacketOutPlayerState(player.getUsername(), true);

        SocketUtils.sendPacket(game, "player-state", activeStatePacket);

        SocketUtils.handleDisconnect(connection, () -> {
            // When the player disconnects, set their state to false.
            player.setState(false);

            // Inform clients that the player is inactive.
            PacketOutPlayerState inactiveStatePacket =
                new PacketOutPlayerState(player.getUsername(), false);

            SocketUtils.sendPacket(game, "player-state", inactiveStatePacket);
        });

        // This should be optimized later on, so that the player uids are not calculated
        // every time someone attempts to join the game.
        Set<String> activePlayerNames = game.getPlayers()
            .stream()
            .filter((p) -> p.getState())
            .map(Player::getUsername)
            .collect(Collectors.toSet());

        return Mono.just(new PacketOutGameInfo(game.getUid(), game.getStatus(), activePlayerNames));
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
