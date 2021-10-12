package io.wordblast.gameserver.modules.game;

// import java.net.Socket;
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
    private final Set<Player> players = new HashSet<>();
    private GameOptions options;
    private GameStatus status;
    private Set<String> usedWords;
    private Player currentPlayer;
    // private Socket connection;

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

    public Set<Player> getPlayers() {
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

    // public Socket getConnection() {
    // return connection;
    // }

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
}
