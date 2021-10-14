package io.wordblast.gameserver.modules.game.packets;

/**
 * The various types of packets sent through and from game socket routes.
 */
public enum PacketType {
    PACKET_IN_GAME_JOIN,
    PACKET_OUT_GAME_INFO,
    PACKET_IN_USERNAME_SELECT,
    PACKET_OUT_USERNAME_SELECT,
    PACKET_OUT_EXCEPTION,
    PACKET_OUT_PLAYER_STATE,
    PACKET_IN_GAME_CHAT_MESSAGE,
    PACKET_IN_CHECK_WORD,
    PACKET_OUT_CHECK_WORD,
    PACKET_OUT_PLAYER_MESSAGE,
    PACKET_IN_PLAYER_MESSAGE,
    PACKET_IN_USERNAME_CHANGE,
    PACKET_OUT_USERNAME_CHANGE;
}
