package io.wordblast.gameserver.modules.task;

import java.util.concurrent.TimeUnit;

/**
 * A task manager implementation.
 */
public final class TaskManager extends AbstractTaskManager {
    /**
     * Singleton isntance of the manager.
     */
    private static final TaskManager INSTANCE = new TaskManager();

    /**
     * Retrieves the singleton instance of the task manager.
     * 
     * @return the singletone instance of the manager.
     */
    public static TaskManager getInstance() {
        return INSTANCE;
    }

    @Override
    public void registerRepeating(final String id,
        final Runnable task,
        final int delay,
        final int period,
        final TimeUnit unit) {
        registerRepeatingAbstract(id, new GameSyncTask(task), delay, period, unit);
    }

    @Override
    public void registerSingle(final String id,
        final Runnable task,
        final int delay,
        final TimeUnit unit) {
        registerSingleAbstract(id, new GameSyncTask(task), delay, unit);
    }
}
