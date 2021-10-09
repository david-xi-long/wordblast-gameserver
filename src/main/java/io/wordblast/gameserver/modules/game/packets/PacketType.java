package io.wordblast.gameserver.modules.game.packets;

/**
 * The various types of packets sent through and from game socket routes.
 */
public enum PacketType {
    PACKET_IN_GAME_JOIN,
    PACKET_OUT_GAME_INFO,
    PACKET_IN_SELECT_USERNAME,
    PACKET_OUT_SELECT_USERNAME,
    PACKET_OUT_EXCEPTION;
}
