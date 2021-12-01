package io.wordblast.gameserver.modules.game;

import io.wordblast.gameserver.modules.authentication.UserService;
import io.wordblast.gameserver.modules.database.UserRepository;
import io.wordblast.gameserver.modules.game.packets.PacketInCheckWord;
import io.wordblast.gameserver.modules.game.packets.PacketInGameJoin;
import io.wordblast.gameserver.modules.game.packets.PacketInPlayerMessage;
import io.wordblast.gameserver.modules.game.packets.PacketInPlayerReadyState;
import io.wordblast.gameserver.modules.game.packets.PacketInSettingChange;
import io.wordblast.gameserver.modules.game.packets.PacketInStartGame;
import io.wordblast.gameserver.modules.game.packets.PacketInUsernameChange;
import io.wordblast.gameserver.modules.game.packets.PacketInUsernameSelect;
import io.wordblast.gameserver.modules.game.packets.PacketOutCheckWord;
import io.wordblast.gameserver.modules.game.packets.PacketOutException;
import io.wordblast.gameserver.modules.game.packets.PacketOutGameInfo;
import io.wordblast.gameserver.modules.game.packets.PacketOutLivesChange;
import io.wordblast.gameserver.modules.game.packets.PacketOutPlayerJoin;
import io.wordblast.gameserver.modules.game.packets.PacketOutPlayerMessage;
import io.wordblast.gameserver.modules.game.packets.PacketOutPlayerQuit;
import io.wordblast.gameserver.modules.game.packets.PacketOutPlayerReadyState;
import io.wordblast.gameserver.modules.game.packets.PacketOutSettingChange;
import io.wordblast.gameserver.modules.game.packets.PacketOutStartGame;
import io.wordblast.gameserver.modules.game.packets.PacketOutUsernameChange;
import io.wordblast.gameserver.modules.game.packets.PacketOutUsernameSelect;
import io.wordblast.gameserver.modules.game.packets.PacketUtils;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
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
        UUID playerUid = packet.getPlayerUid();

        Game game = GameManager.getGame(gameUid);

        if (game == null) {
            return Mono.error(new GameNotFoundException());
        }

        if (game.getStatus() == GameStatus.STARTED) {
            return Mono.error(new GameInProgressException());
        }

        boolean usernameExists = game.getPlayers()
            .stream()
            .anyMatch((p) -> p.getUsername().equalsIgnoreCase(username));

        if (usernameExists) {
            return Mono.error(new UsernameTakenException());
        }

        int defaultLives = game.getGameOptions().getLivesPerPlayer();

        Player player = new Player(username, playerUid);
        player.setConnection(connection);
        player.setLives(defaultLives);

        game.addPlayer(player);

        // Inform clients that the player is active.
        SocketUtils.sendPacket(game, "player-join",
            new PacketOutPlayerJoin(PlayerInfo.of(player)));

        SocketUtils.handleDisconnect(connection, () -> {
            // When the player disconnects, set their state to false.
            player.setState(PlayerState.DISCONNECTED);

            // Inform clients that the player is inactive.
            SocketUtils.sendPacket(game, "player-quit",
                new PacketOutPlayerQuit(PlayerInfo.of(player)));
        });

        GameOptions options = game.getGameOptions();
        Map<String, String> settings = new LinkedHashMap<>();

        settings.put("public",
            String.valueOf(options.getVisibility() == GameVisibility.PUBLIC ? true : false));
        settings.put("playerLives", String.valueOf(options.getLivesPerPlayer()));
        settings.put("timePerPlayer", String.valueOf(options.getTimePerPlayer()));
        settings.put("extraLives", String.valueOf(options.earnsExtraLives()));
        settings.put("increasingDifficulty", String.valueOf(options.increasesDifficulty()));

        Set<PlayerInfo> activePlayerInfos = game.getPlayers()
            .stream()
            .filter((p) -> p.getState() == PlayerState.ACTIVE)
            .map(PlayerInfo::of)
            .collect(Collectors.toSet());

        return Mono.just(
            new PacketOutGameInfo(
                game.getUid(),
                game.getStatus(),
                activePlayerInfos,
                game.getOwner(),
                settings));
    }

    /**
     * Handles players sending chat messages in game.
     * 
     * @param packet the packet to handle.
     * @return the packet response.
     */
    @MessageMapping("chat-message")
    public Mono<Void> fireAndForget(@Payload PacketInPlayerMessage packet) {
        PacketOutPlayerMessage message = new PacketOutPlayerMessage(packet.getGameUid(),
            packet.getUsername(), packet.getMessage());
        Game game = GameManager.getGame(packet.getGameUid());
        SocketUtils.sendPacket(game, "chat-message", message);
        return Mono.empty();
    }

    /**
     * Handles players typing a word while on their turn.
     * 
     * @param packet the packet to handle.
     * @return the packet response.
     */
    @MessageMapping("update-word")
    public Mono<Void> fireAndForget2(@Payload PacketInPlayerMessage packet) {
        PacketOutPlayerMessage message = new PacketOutPlayerMessage(packet.getGameUid(),
            packet.getUsername(), packet.getMessage());
        Game game = GameManager.getGame(packet.getGameUid());
        SocketUtils.sendPacket(game, "update-word", message);
        return Mono.empty();
    }

    /**
     * Handles players selecting their username within a game.
     * 
     * @param packet the packet to handle.
     * @return the packet response.
     */
    @MessageMapping("select-username")
    public Mono<PacketOutUsernameSelect> selectUsername(PacketInUsernameSelect packet) {
        UUID gameUid = packet.getGameUid();
        String username = packet.getUsername();

        Game game = GameManager.getGame(gameUid);

        if (game == null) {
            return Mono.error(new GameNotFoundException());
        }

        boolean usernameExists = game.getPlayers()
            .stream()
            .anyMatch((p) -> p.getUsername().equalsIgnoreCase(username));

        return Mono.just(new PacketOutUsernameSelect(usernameExists));
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

    /**
     * Handles players sending a word as an input to be checked.
     * 
     * @param packet the packet to handle.
     * @return the packet response.
     */
    @MessageMapping("check-word")
    public Mono<PacketOutCheckWord> checkWord(PacketInCheckWord packet) {
        Exception err = PacketUtils.validateGame(packet);
        if (err != null) {
            return Mono.error(err);
        }

        String guess = packet.getWord();
        UUID gameUid = packet.getGameUid();

        Game game = GameManager.getGame(gameUid);

        return game.getController()
            .checkEndTurn(guess)
            .map((info) -> new PacketOutCheckWord(!info.isEmpty()));
    }

    /**
     * Handles players changing their usernames in a game.
     * 
     * @param packet the packet to handle.
     * @return the packet response.
     */
    @MessageMapping("change-username")
    public Mono<PacketOutUsernameChange> changeUsername(PacketInUsernameChange packet) {
        UUID gameUid = packet.getGameUid();
        String oldUsername = packet.getOldUsername();
        String newUsername = packet.getNewUsername();

        Game game = GameManager.getGame(gameUid);

        if (game == null) {
            return Mono.error(new GameNotFoundException());
        }

        Player playerWithOldUsername = game.getPlayers()
            .stream()
            .filter((p) -> p.getUsername().equalsIgnoreCase(oldUsername))
            .findFirst()
            .orElse(null);

        if (playerWithOldUsername == null) {
            return Mono.error(new PlayerNotFoundException());
        }

        boolean usernameExists = game.getPlayers()
            .stream()
            .filter((p) -> p.getState() == PlayerState.ACTIVE)
            .anyMatch((p) -> p.getUsername().equalsIgnoreCase(newUsername));

        if (usernameExists) {
            return Mono.error(new UsernameTakenException());
        }

        // Validation checks passed, change username and echo the packet.
        playerWithOldUsername.setUsername(newUsername);

        PacketOutUsernameChange echoPacket =
            new PacketOutUsernameChange(gameUid, oldUsername, newUsername);

        SocketUtils.sendPacket(game, "change-username", echoPacket);

        return Mono.just(echoPacket);
    }

    /**
     * Handles the start game request.
     * 
     * @param packet the packet to handle.
     * @return the packet response.
     */
    @MessageMapping("start-game")
    public Mono<Void> startGame(@Payload PacketInStartGame packet) {
        UUID gameUid = packet.getGameUid();
        Game game = GameManager.getGame(gameUid);
        String[] players = new String[game.getPlayers().size()];
        for (int i = 0; i < players.length; i++) {
            players[i] = game.getPlayers().get(i).getUsername();
        }
        game.setStatus(GameStatus.STARTED);
        PacketOutStartGame outPacket = new PacketOutStartGame(gameUid, players);
        SocketUtils.sendPacket(game, "start-game", outPacket);
        return Mono.empty();
    }

    /**
     * Handles when a player changes their ready state.
     * 
     * @param statePacket the packet to handle.
     * @return the packet response.
     */
    @MessageMapping("player-ready-state")
    public Mono<Void> playerReady(PacketInPlayerReadyState statePacket) {
        Exception err;

        // Validate that the game exists.
        err = PacketUtils.validateGame(statePacket);
        if (err != null) {
            return Mono.error(err);
        }

        // Validate that the player exists.
        err = PacketUtils.validatePlayer(statePacket);
        if (err != null) {
            return Mono.error(err);
        }

        UUID gameUid = statePacket.getGameUid();
        Game game = GameManager.getGame(gameUid);
        String username = statePacket.getUsername();

        Player player = game.getPlayers()
            .stream()
            .filter((p) -> p.getUsername().equals(username))
            .findFirst()
            .orElse(null);

        // Set the ready state of the player.
        player.setReady(statePacket.isReady());

        // Echo the packet to all connected players.
        SocketUtils.sendPacket(game, "player-ready-state",
            new PacketOutPlayerReadyState(gameUid, username, statePacket.isReady()));

        // Start the game if all players are ready.
        game.getController()
            .startGameIfReady();

        return Mono.empty();
    }

    /**
     * Handles when a game owner has changed a setting within a game.
     * 
     * @param packet the packet to handle.
     * @return the packet response.
     */
    @MessageMapping("setting-change")
    public Mono<Void> settingChange(PacketInSettingChange packet) {
        // Validate the game exists.
        Exception err = PacketUtils.validateGame(packet);
        if (err != null) {
            return Mono.error(new GameNotFoundException());
        }

        UUID gameUid = packet.getGameUid();
        String setting = packet.getSetting();
        String value = packet.getValue();

        Game game = GameManager.getGame(gameUid);
        GameOptions options = game.getGameOptions();

        // If available, modify the setting.
        switch (setting) {
            case "public":
                options.setVisibility(
                    Boolean.valueOf(value) ? GameVisibility.PUBLIC : GameVisibility.PRIVATE);
                break;
            case "playerLives":
                int newLives = Integer.valueOf(value);

                options.setLivesPerPlayer(newLives);

                for (Player player : game.getPlayers()) {
                    player.setLives(newLives);

                    SocketUtils.sendPacket(game, "lives-change",
                        new PacketOutLivesChange(player.getUsername(), newLives));
                }

                break;
            case "timePerPlayer":
                options.setTimePerPlayer(Integer.valueOf(value));
                break;
            case "extraLives":
                options.setEarnExtraLives(Boolean.valueOf(value));
                break;
            case "increasingDifficulty":
                options.setIncreaseDifficulty(Boolean.valueOf(value));
                break;
            default:
                return Mono.error(new GameSettingNotFoundException());
        }

        // Echo the packet to all the game clients.
        SocketUtils.sendPacket(game, "setting-change",
            new PacketOutSettingChange(gameUid, setting, value));

        return Mono.empty();
    }
}
