package io.wordblast.gameserver.modules.game;

import io.wordblast.gameserver.modules.game.packets.PacketOutPlayerEliminated;
import io.wordblast.gameserver.modules.game.packets.PacketUtils;
import io.wordblast.gameserver.modules.task.TaskManager;
import io.wordblast.gameserver.modules.word.WordInfo;
import io.wordblast.gameserver.modules.word.WordManager;
import io.wordblast.gameserver.modules.word.WordUtils;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import reactor.core.publisher.Mono;

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
            .filter((p) -> p.getState() == PlayerState.ACTIVE)
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

        for (Player p : game.getPlayers()) {
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
        game.setCurrentLetterCombo(WordUtils.getCombination());

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
            game.setPreviousPlayer(game.getCurrentPlayer());
            game.setCurrentPlayer(null);
            nextRound();
            return;
        }

        // Get the next available player.
        for (int i = nextIndex; i < players.size(); i++) {
            nextPlayer = players.get(i);
            if (nextPlayer.getState() == PlayerState.ACTIVE) {
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
                    endTurn(true);
                    nextTurn();
                },
                turnLength,
                TimeUnit.SECONDS);

        // Send a round info packet to the connected clients.
        PacketUtils.sendRoundInfo(game);
    }

    /**
     * Checks if the end should end. If it should, the controller will end the turn and move on to
     * the next turn.
     * 
     * @param guess the guess sent by the player.
     * @return An optional object which contains a word info object if the turn was ended, otherwise
     *         an empty optional object.
     */
    public Mono<Optional<WordInfo>> checkEndTurn(String guess) {
        Set<String> usedWords = game.getWords();
        String lowerCaseGuess = guess.toLowerCase();
        String combo = game.getCurrentLetterCombo().toLowerCase();
        if (usedWords.contains(guess)
            || !lowerCaseGuess.contains(combo)
            || !WordManager.getParsedWords().containsKey(lowerCaseGuess)) {
            return Mono.just(Optional.empty());
        }

        usedWords.add(guess);

        Player currentPlayer = game.getCurrentPlayer();
        Set<Character> usedChars = currentPlayer.getUsedChars();
        Set<Character> unusedChars = currentPlayer.getUnusedChars();
        Set<Character> newlyUsedChars = currentPlayer.getNewlyUsedChars();

        newlyUsedChars.clear();

        for (int i = 0; i < guess.length(); i++) {
            char curChar = guess.charAt(i);
            if (unusedChars.contains(curChar)) {
                unusedChars.remove(curChar);
                usedChars.add(curChar);
                newlyUsedChars.add(curChar);
            }
        }

        if (unusedChars.size() == 0) {
            currentPlayer.resetChars();
            currentPlayer.setLives(currentPlayer.getLives() + 1);
        }

        endTurn(false);
        nextTurn();

        return WordUtils.getWordInfo(guess);
    }

    /**
     * Ends the current turn.
     */
    public void endTurn(boolean outOfTime) {
        Player currentPlayer = game.getCurrentPlayer();

        if (outOfTime) {
            currentPlayer.setLives(currentPlayer.getLives() - 1);
            checkElimination(currentPlayer);
        }

        game.setPreviousOutOfTime(outOfTime);
    }

    /**
     * Will eliminate the player if the constraints are meet to do so.
     * 
     * @param player the player to check.
     * @return {@code true} if the player was eliminated, otherwise {@code false}.
     */
    public boolean checkElimination(Player player) {
        if (player.getLives() != 0) {
            return false;
        }

        eliminate(player);

        return true;
    }

    /**
     * Eliminates the player from the game.
     * 
     * @param player the player to eliminate.
     */
    public void eliminate(Player player) {
        player.setState(PlayerState.ELIMINATED);

        SocketUtils.sendPacket(game, "player-eliminated",
            new PacketOutPlayerEliminated(player.getUsername()));
    }
}
