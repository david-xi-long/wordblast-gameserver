package io.wordblast.gameserver.modules.game;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

/**
 * A class which implements a countdown clock.
 */
public class Countdown {
    private final int length;
    private final TemporalUnit unit;

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime stopDate;

    /**
     * Creates a new Countdown instance.
     * 
     * @param length the length of the countdonw.
     * @param unit the unit to use for the length.
     */
    public Countdown(int length, TemporalUnit unit) {
        this.length = length;
        this.unit = unit;
    }

    /**
     * Start the countdown.
     */
    public void start() {
        if (startDate != null) {
            return;
        }

        this.startDate = LocalDateTime.now();
        this.endDate = startDate.plus(length, unit);
    }

    /**
     * Stop the countdown.
     */
    public void stop() {
        if (stopDate != null) {
            return;
        }

        this.stopDate = LocalDateTime.now();
    }

    /**
     * Reset the countdown.
     */
    public void reset() {
        this.startDate = null;
        this.endDate = null;
        this.stopDate = null;
    }

    /**
     * Retrieve the time elapsed of the countdown.
     * 
     * @return the time elapsed, in milliseconds.
     */
    public long getTimeElapsed() {
        LocalDateTime reference = stopDate == null ? LocalDateTime.now() : stopDate;
        return startDate != null ? startDate.until(reference, ChronoUnit.MILLIS) : -1;
    }

    /**
     * Retrieve the time remaining of the countdown.
     * 
     * @return the time remaining, in milliseconds.
     */
    public long getTimeRemaining() {
        LocalDateTime reference = stopDate == null ? LocalDateTime.now() : stopDate;
        return endDate != null ? reference.until(endDate, ChronoUnit.MILLIS) : -1;
    }

    /*
     * Returns the original length.
     */
    public int getLength() {
        return length;
    }
}
