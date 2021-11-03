package io.wordblast.gameserver.modules.game.packets;

import java.util.UUID;

/**
 * A packet which is received when a setting has been changed within a game by the game owner.
 */
public class PacketInSettingChange extends Packet implements GamePacket {
    private final UUID gameUid;
    private final String setting;
    private final String value;

    /**
     * Creates a new PacketInSettingChange instance.
     * 
     * @param gameUid the unique identifier of the game.
     * @param setting the setting that was changed.
     * @param value the new value of the setting.
     */
    public PacketInSettingChange(UUID gameUid, String setting, String value) {
        super(PacketType.PACKET_IN_SETTING_CHANGE);
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
