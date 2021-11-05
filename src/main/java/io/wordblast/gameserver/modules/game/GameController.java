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
        // Only count the players that are currently in the game.
        boolean allReady = game.getPlayers()
            .stream()
            .filter((p) -> p.getState())
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
        
        for (Player p: game.getPlayers()) {
            p.setLives(game.getGameOptions().getLivesPerPlayer());
        }

        game.setStatus(GameStatus.STARTED);
        game.setPreviousOutOfTime(false);

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
        Player nextPlayer = null;

        // Get the index of the next player to check.
        int nextIndex = currentPlayer != null ? players.indexOf(currentPlayer) + 1 : 0;

        // If all players have gone, start a new round.
        if (nextIndex == players.size()) {
            game.setCurrentPlayer(null);
            nextRound();
            return;
        }

        // Get the next available player.
        for (int i = nextIndex; i < players.size(); i++) {
            nextPlayer = players.get(i);
            if (nextPlayer.getState()) {
                break;
            }
        }

        // Start the turn of the next player.
        startTurn(nextPlayer);
        
    }

    /**
     * Starts a new turn with the player.
     * 
     * @param player the player whose turn should be started.
     */
    public void startTurn(Player player) {
        // Set the current and previous player of the game.
        game.setPreviousPlayer(game.getCurrentPlayer());
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
                () -> {
                    // Decrement current player's lives since timer elapsed
                    player.setLives(player.getLives() - 1);
                    if (player.getLives() == 0) {
                        game.removePlayer(player);
                    }
                    game.setPreviousOutOfTime(true);
                    nextTurn();
                },
                turnLength,
                TimeUnit.SECONDS);

        // Send a round info packet to the connected clients.
        
        PacketUtils.sendRoundInfo(game);
    }
}
