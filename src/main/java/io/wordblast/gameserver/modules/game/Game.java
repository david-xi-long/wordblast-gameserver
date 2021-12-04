package io.wordblast.gameserver.modules.game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.wordblast.gameserver.modules.word.WordUtils;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * The game resource.
 */
@Document(collection = "games")
public class Game {
    @JsonIgnore
    @Transient
    private final GameController controller = new GameController(this);

    private final UUID uid;
    private final String sid;
    private final List<Player> players = new ArrayList<>();
    private final Set<Player> alivePlayers = new HashSet<>();

    private GameOptions options = new GameOptions();
    private GameStatus status;
    private Set<String> usedWords;
    private Set<String> usedLetterCombinations;
    private String currentLetterCombo;
    private Player currentPlayer;
    private int currentRound;
    private Countdown countdown;
    private UUID ownerUid;
    private Player previousPlayer;
    private boolean previousOutOfTime;

    /**
     * Create a new Game instance.
     */
    public Game() {
        this(UUID.randomUUID(), WordUtils.generateRandomString(6));
        this.usedWords = new HashSet<>();
        this.usedLetterCombinations = new HashSet<>();
    }

    /**
     * Create a new Game intance.
     * 
     * @param uid the unique identifier of the game.
     */
    public Game(final UUID uid, final String sid) {
        this.uid = uid;
        this.sid = sid;
        this.status = GameStatus.WAITING;
        this.usedWords = new HashSet<>();

    }

    public GameController getController() {
        return controller;
    }

    public UUID getUid() {
        return uid;
    }

    public String getSid() {
        return sid;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public List<Player> getPlayers() {
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

    public Player getPreviousPlayer() {
        return previousPlayer;
    }

    public void setPreviousPlayer(Player previousPlayer) {
        this.previousPlayer = previousPlayer;
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

    public int getCurrentRound() {
        return currentRound;
    }

    public void setCurrentRound(int currentRound) {
        this.currentRound = currentRound;
    }

    public Countdown getCountdown() {
        return countdown;
    }

    public void setCountdown(Countdown countdown) {
        this.countdown = countdown;
    }

    public UUID getOwner() {
        return ownerUid;
    }

    public void setOwner(UUID ownerUid) {
        this.ownerUid = ownerUid;
    }

    public boolean getPreviousOutOfTime() {
        return previousOutOfTime;
    }

    public void setPreviousOutOfTime(boolean previousOutOfTime) {
        this.previousOutOfTime = previousOutOfTime;
    }
}
