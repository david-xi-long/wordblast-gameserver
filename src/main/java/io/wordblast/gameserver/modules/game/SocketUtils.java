package io.wordblast.gameserver.modules.game;

import io.wordblast.gameserver.modules.game.packets.Packet;
import java.util.stream.Stream;
import org.springframework.messaging.rsocket.RSocketRequester;

/**
 * A utility class containing socket related helper methods.
 */
public final class SocketUtils {
    /**
     * Class cannot be instantiated.
     */
    private SocketUtils() {
        throw new IllegalStateException("Class cannot be instantiated.");
    }

    /**
     * Sets a handler for when the socket client disconnects.
     * 
     * @param connection the connection to add the handler to.
     * @param runnable the callback to invoke when the client disconnects.
     */
    public static void handleDisconnect(RSocketRequester connection, Runnable runnable) {
        connection.rsocket()
            .onClose()
            .toFuture()
            .thenRun(runnable);
    }

    /**
     * Sends a packet to all the players within a game.
     * 
     * @param game the game to send the packet to.
     * @param route the route to send the data to.
     * @param packet the packet to send.
     */
    public static void sendPacket(Game game, String route, Packet packet) {
        sendData(
            game.getPlayers()
                .stream()
                .map(Player::getConnection),
            route,
            packet);
    }

    /**
     * Sends a packet to the player.
     * 
     * @param player the player to send the packet to.
     * @param route the route to send the data to.
     * @param packet the packet to send.
     */
    public static void sendPacket(Player player, String route, Packet packet) {
        sendData(player.getConnection(), route, packet);
    }

    /**
     * Sends data to all the connections within a stream.
     * 
     * @param connections the connections to send the data to.
     * @param route the route to send the data to.
     * @param data the data to send.
     */
    public static void sendData(Stream<RSocketRequester> connections, String route, Object data) {
        connections.forEach((c) -> sendData(c, route, data));
    }

    /**
     * Sends data to the connection.
     * 
     * @param connection the connection to send the data to.
     * @param route the route to send the data to.
     * @param data the data to send.
     */
    public static void sendData(RSocketRequester connection, String route, Object data) {
        connection.route(route)
            .data(data)
            .send()
            .subscribe();
    }
}
