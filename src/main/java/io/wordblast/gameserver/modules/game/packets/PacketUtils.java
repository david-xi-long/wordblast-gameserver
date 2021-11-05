package io.wordblast.gameserver.modules.game.packets;

import io.wordblast.gameserver.modules.game.Game;
import io.wordblast.gameserver.modules.game.GameManager;
import io.wordblast.gameserver.modules.game.GameNotFoundException;
import io.wordblast.gameserver.modules.game.Player;
import io.wordblast.gameserver.modules.game.PlayerNotFoundException;
import io.wordblast.gameserver.modules.game.SocketUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * A class which provides utility methods for packet related actions.
 */
public final class PacketUtils {
    /**
     * Class cannot be instantiated.
     */
    private PacketUtils() {
        throw new IllegalStateException("Class cannot be instantiated.");
    }

    /**
     * Sends out a round info packet to the game.
     * 
     * @param game the game to send the packet to.
     */
    public static void sendRoundInfo(Game game) {
        
        UUID gameUid = game.getUid();
        int round = game.getCurrentRound();
        String player = game.getCurrentPlayer().getUsername();
        long timeRemaining = game.getCountdown().getTimeRemaining();
        String[] players = new String[game.getPlayers().size()];
        int[] playerLives = new int[game.getPlayers().size()];
        for (int i = 0; i < game.getPlayers().size(); i++) {
            players[i] = game.getPlayers().get(i).getUsername();
            playerLives[i] = game.getPlayers().get(i).getLives();
        }
        String previousPlayer = "";
        if (game.getPreviousPlayer() != null) {
            previousPlayer = game.getPreviousPlayer().getUsername();
        }
        String notificationText = "";
        
        if (game.getPreviousOutOfTime()) {
            notificationText = "You ran out of time!";
        } else {
            if (!previousPlayer.equals("")) {
                List<Character> newlyUsed = game.getPreviousPlayer().getNewlyUsedChars();
                notificationText = "Your new characters are: ";
                for (Character c: newlyUsed) {
                    notificationText += c;
                    notificationText += ' ';
                }
                if (game.getPreviousPlayer().getUsedChars().size() == 0) {
                    notificationText += ". You have also gained a life";
                }
            }
        }
        
        
        
        System.out.println(notificationText);

        SocketUtils.sendPacket(game, "round-info",
            new PacketOutRoundInfo(gameUid, round, player, timeRemaining, players, playerLives, previousPlayer, notificationText, game.getCurrentLetterCombo()));
    }

    /**
     * Validates whether the game packet refers to an active game.
     * 
     * @param packet the packet to validate.
     * @return if validation failed,
     *         {@link io.wordblast.gameserver.modules.game.GameNotFoundException
     *         GameNotFoundException}. otherwise {@code null}.
     */
    public static Exception validateGame(GamePacket packet) {
        UUID gameUid = packet.getGameUid();
        Game game = GameManager.getGame(gameUid);
        return game != null ? null : new GameNotFoundException();
    }

    /**
     * Validates whether the game packet refers to a valid player within the game.
     * 
     * @param <T> a packet which extends both
     *        {@link io.wordblast.gameserver.modules.game.packets.GamePacket GamePacket} and
     *        {@link io.wordblast.gameserver.modules.game.packets.PlayerPacket PlayerPacket}.
     * @param packet the packet to validate.
     * @return if validation failed,
     *         {@link io.wordblast.gameserver.modules.game.PlayerNotFoundException
     *         PlayerNotFoundException}. otherwise {@code null}.
     */
    public static <T extends GamePacket & PlayerPacket> Exception validatePlayer(T packet) {
        UUID gameUid = packet.getGameUid();
        String username = packet.getUsername();
        Game game = GameManager.getGame(gameUid);
        boolean playerExists = game.getPlayers()
            .stream()
            .anyMatch((p) -> p.getUsername().endsWith(username));
        return playerExists ? null : new PlayerNotFoundException();
    }
}
