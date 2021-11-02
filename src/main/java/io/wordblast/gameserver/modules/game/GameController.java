package io.wordblast.gameserver.modules.game;

import io.wordblast.gameserver.modules.game.packets.PacketUtils;
import io.wordblast.gameserver.modules.task.TaskManager;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * The class which controls the logic of a game.
 */
public class GameController {
    private final Game game;

    public GameController(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    /**
     * Starts the game, if necessary.
     * 
     * @return {@code true} if the game was started, otherwise {@code false}.
     */
    public boolean startGameIfReady() {
        // Do not start the game if it already has started.
        if (game.getStatus().compareTo(GameStatus.STARTED) > 0) {
            return false;
        }

        // Do not start the game if not all players are ready.
        boolean allReady = game.getPlayers()
            .stream()
            .allMatch((p) -> p.isReady());

        if (!allReady) {
            return false;
        }

        // Start the game.
        startGame();

        return true;
    }

    /**
     * Starts the game.
     */
    public void startGame() {
        // Do not start the game if it already has started.
        if (game.getStatus().compareTo(GameStatus.STARTED) > 0) {
            return;
        }

        game.setStatus(GameStatus.STARTED);

        // Start the first round.
        nextRound();
    }

    /**
     * Starts the next round.
     */
    public void nextRound() {
        game.setCurrentRound(game.getCurrentRound() + 1);

        // Start the first player's turn.
        nextTurn();
    }

    /**
     * Starts the next player turn.
     */
    public void nextTurn() {
        List<Player> players = game.getPlayers();
        Player currentPlayer = game.getCurrentPlayer();
        Player nextPlayer;

        if (currentPlayer == null) {
            nextPlayer = players.get(0);
        } else {
            int nextIndex = players.indexOf(currentPlayer) + 1;

            if (nextIndex == players.size()) {
                game.setCurrentPlayer(null);
                nextRound();
                return;
            }

            nextPlayer = players.get(nextIndex);
        }

        startTurn(nextPlayer);
    }

    /**
     * Starts a new turn with the player.
     * 
     * @param player the player whose turn should be started.
     */
    public void startTurn(Player player) {
        // Set the current player of the game.
        game.setCurrentPlayer(player);

        // Set up the turn countdown.
        int turnLength = game.getGameOptions().getTimePerPlayer();

        Countdown turnCountdown = new Countdown(turnLength, ChronoUnit.SECONDS);
        turnCountdown.start();
        game.setCountdown(turnCountdown);

        // Set up task to end the turn.
        String taskName = "turnCountdown:" + game.getUid();

        TaskManager.getInstance()
            .unregister(taskName);

        TaskManager.getInstance()
            .registerSingle(
                taskName,
                () -> nextTurn(),
                turnLength,
                TimeUnit.SECONDS);

        // Send a round info packet to the connected clients.
        PacketUtils.sendRoundInfo(game);
    }
}
