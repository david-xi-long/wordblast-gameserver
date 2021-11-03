package io.wordblast.gameserver.modules.game.packets;

import java.util.UUID;

/**
 * A packet which is echo'd out to the clients of a game when a game owner changes a setting within
 * the game.
 */
public class PacketOutSettingChange extends Packet {
    private final UUID gameUid;
    private final String setting;
    private final String value;

    /**
     * Creates a new PacketOutSettingChange instance.
     * 
     * @param gameUid the unique identifier of the game.
     * @param setting the setting that was changed.
     * @param value the new value of the setting.
     */
    public PacketOutSettingChange(UUID gameUid, String setting, String value) {
        super(PacketType.PACKET_OUT_SETTING_CHANGE);
        this.gameUid = gameUid;
        this.setting = setting;
        this.value = value;
    }

    public UUID getGameUid() {
        return gameUid;
    }

    public String getSetting() {
        return setting;
    }

    public String getValue() {
        return value;
    }
}
