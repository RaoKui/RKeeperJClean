package com.rk.rkeeper.data.local;

import android.provider.ContactsContract;
import android.support.annotation.NonNull;

import com.rk.rkeeper.data.TaskDataSource;
import com.rk.rkeeper.task.domain.Task;
import com.rk.rkeeper.utils.AppExecutors;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class TaskLocalDataSource implements TaskDataSource {

    private static volatile TaskLocalDataSource INSTANCE;

    private TaskDao mTaskDao;
    private AppExecutors mAppExecutors;

    private TaskLocalDataSource(@NonNull AppExecutors appExecutors, @NonNull TaskDao taskDao) {
        mTaskDao = taskDao;
        mAppExecutors = appExecutors;
    }

    public static TaskLocalDataSource getInstance(@NonNull AppExecutors appExecutors, @NonNull TaskDao taskDao) {
        if (INSTANCE == null) {
            synchronized (TaskLocalDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TaskLocalDataSource(appExecutors, taskDao);
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void getTasks(@NonNull final LoadTasksCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<Task> tasks = mTaskDao.getTasks();
                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (tasks.isEmpty()) {
                            callback.onDataNotAvailable();
                        } else {
                            callback.onTasksLoaded(tasks);
                        }
                    }
                });
            }
        };

        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void getTask(@NonNull String taskId, @NonNull GetTaskCallback callback) {

    }

    @Override
    public void saveTask(@NonNull final Task task) {
        checkNotNull(task);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mTaskDao.insertTask(task);
            }
        };
        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void completeTask(@NonNull final Task task) {
        Runnable completedRunnable = new Runnable() {
            @Override
            public void run() {
                mTaskDao.updateCompleted(task.getId(), true);
            }
        };
        mAppExecutors.diskIO().execute(completedRunnable);
    }

    @Override
    public void completeTask(@NonNull String taskId) {

    }

    @Override
    public void activateTask(@NonNull Task task) {

    }

    @Override
    public void activateTask(@NonNull String taskId) {

    }

    @Override
    public void clearCompletedTasks() {

    }

    @Override
    public void refreshTasks() {

    }

    @Override
    public void deleteAllTasks() {

    }

    @Override
    public void deleteTask(@NonNull String taskId) {

    }
}
