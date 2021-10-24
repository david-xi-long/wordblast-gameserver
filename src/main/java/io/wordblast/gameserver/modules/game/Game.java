package io.wordblast.gameserver.modules.game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * The game resource.
 */
@Document(collection = "games")
public class Game {
    private final UUID uid;
    private final ArrayList<Player> players = new ArrayList<>();
    private final Set<Player> alivePlayers = new HashSet<>();
    private GameOptions options = new GameOptions();
    private GameStatus status;
    private Set<String> usedWords;
    private Set<String> usedLetterCombinations;
    private String currentLetterCombo;
    private Player currentPlayer;

    public Game() {
        this(UUID.randomUUID());
    }

    public Game(final UUID uid) {
        this.uid = uid;
        this.status = GameStatus.WAITING;
    }

    public UUID getUid() {
        return uid;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public Set<String> getWords() {
        return usedWords;
    }

    public void addWord(String word) {
        usedWords.add(word);
    }

    public Set<String> getLetterCombinations() {
        return usedLetterCombinations;
    }

    public void addLetterCombination(String letterCombo) {
        usedLetterCombinations.add(letterCombo);
    }

    public void setGameOptions(GameOptions options) {
        this.options = options;
    }

    public GameOptions getGameOptions() {
        return options;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Set<Player> getAlivePlayers() {
        return alivePlayers;
    }

    public void addAlivePlayer(Player player) {
        alivePlayers.add(player);
    }

    public void removeAlivePlayer(Player player) {
        alivePlayers.remove(player);
    }

    public void setCurrentLetterCombo(String letterCombo) {
        this.currentLetterCombo = letterCombo;
    }

    public String getCurrentLetterCombo() {
        return this.currentLetterCombo;
    }
}
