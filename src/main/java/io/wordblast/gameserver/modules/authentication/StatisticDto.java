package io.wordblast.gameserver.modules.authentication;

/**
 * The data transfer object for statistics.
 */
public class StatisticDto {
    private String uid;

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }
}
