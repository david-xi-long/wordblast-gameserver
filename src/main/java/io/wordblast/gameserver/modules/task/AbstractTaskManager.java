package io.wordblast.gameserver.modules.task;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * A class which manages tasks.
 */
public abstract class AbstractTaskManager {
    /**
     * The executor service that will be used to execute all tasks within the task manager.
     */
    private static final ScheduledExecutorService SERVICE =
        Executors.newSingleThreadScheduledExecutor();

    /**
     * The tasks of the task manager.
     */
    private final Map<String, ScheduledFuture<?>> tasks = new HashMap<>();

    /**
     * Registers a repeating task with the task manager.
     * 
     * @param id the unique identifier to use for the task.
     * @param task the task to run.
     * @param delay the amount of time to wait before running the task for the first time.
     * @param period the amount of time to wait between each run of the task.
     * @param unit the time unit to use for the period and delay.
     */
    protected void registerRepeatingAbstract(final String id,
        final SyncTask task,
        final int delay,
        final int period,
        final TimeUnit unit) {
        tasks.put(
            id,
            SERVICE.scheduleAtFixedRate(
                task,
                delay,
                period,
                unit));
    }

    /**
     * Registers a single task with the task manager.
     * 
     * @param id the unique identifier to use for the task.
     * @param task the task to run.
     * @param delay the amount of time to wait before running the task.
     * @param unit the time unit to use for the delay.
     */
    protected void registerSingleAbstract(final String id,
        final SyncTask task,
        final int delay,
        final TimeUnit unit) {
        tasks.put(
            id,
            SERVICE.schedule(
                () -> {
                    unregister(id);
                    task.run();
                },
                delay,
                unit));
    }

    /**
     * Unregisters the task with the provided id from the task manager.
     * 
     * @param id the id of the task.
     */
    public void unregister(final String id) {
        Optional.ofNullable(tasks.get(id))
            .ifPresent(t -> {
                t.cancel(false);
                tasks.remove(id);
            });
    }

    /**
     * Checks if the task with the provided id exists in the task manager.
     * 
     * @param id the id of the task to check.
     * @return {@code true} if the task exists, otherwise {@code false}.
     */
    public boolean exists(final String id) {
        return tasks.containsKey(id);
    }

    /**
     * Registers a repeating task with the task manager.
     * 
     * @param id the unique identifier to use for the task.
     * @param task the task to run.
     * @param delay the amount of time to wait before running the task for the first time.
     * @param period the amount of time to wait between each run of the task.
     * @param unit the time unit to use for the period and delay.
     */
    public abstract void registerRepeating(String id,
        Runnable task,
        int delay,
        int period,
        TimeUnit unit);

    /**
     * Registers a single task with the task manager.
     * 
     * @param id the unique identifier to use for the task.
     * @param task the task to run.
     * @param delay the amount of time to wait before running the task.
     * @param unit the time unit to use for the delay.
     */
    public abstract void registerSingle(String id, Runnable task, int delay, TimeUnit unit);
}
