package io.wordblast.gameserver.modules.task;

/**
 * A synchronous task implementation.
 */
public final class GameSyncTask implements SyncTask {
    /**
     * The task to run synchronously.
     */
    private Runnable task;

    /**
     * Creates a synchronously task.
     * 
     * @param task the task to run synchronously.
     */
    public GameSyncTask(final Runnable task) {
        this.task = task;
    }

    @Override
    public void run() {
        task.run();
    }
}
