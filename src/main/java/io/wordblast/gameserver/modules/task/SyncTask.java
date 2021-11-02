package io.wordblast.gameserver.modules.task;

/**
 * A synchronous task.
 */
@FunctionalInterface
public interface SyncTask extends Runnable {
    /**
     * Runs the task synchronously.
     */
    void run();
}
