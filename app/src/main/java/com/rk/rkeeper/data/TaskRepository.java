package com.rk.rkeeper.data;

import android.support.annotation.NonNull;
import android.util.ArrayMap;

import com.rk.rkeeper.task.domain.Task;

import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

public class TaskRepository implements TaskDataSource {

    private static TaskRepository INSTANCE = null;
    private final TaskDataSource mTaskRemoteDataSource;
    private final TaskDataSource mTaskLocalDataSource;

    Map<String, Task> mCachedTasks;

    private boolean mCacheIsDirty = false;

    private TaskRepository(@NonNull TaskDataSource taskRemoteDataSource, @NonNull TaskDataSource taskLocalDataSource) {
        this.mTaskRemoteDataSource = checkNotNull(taskRemoteDataSource);
        this.mTaskLocalDataSource = checkNotNull(taskLocalDataSource);
    }

    public static TaskRepository getInstance(@NonNull TaskDataSource taskRemoteDataSource, @NonNull TaskDataSource taskLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new TaskRepository(taskRemoteDataSource, taskLocalDataSource);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public void getTasks(@NonNull LoadTasksCallback callback) {
        checkNotNull(callback);

        if (mCachedTasks != null && !mCacheIsDirty) {
            callback.onTasksLoaded(new ArrayList<Task>(mCachedTasks.values()));
            return;
        }
        if (mCacheIsDirty) {
            getTasksFromRemoteDataSource(callback);
        }
    }

    private void getTasksFromRemoteDataSource(@NonNull final LoadTasksCallback callback) {
        mTaskRemoteDataSource.getTasks(new LoadTasksCallback() {
            @Override
            public void onTasksLoaded(List<Task> tasks) {
                refreshCache(tasks);
                refreshLocalDataSource(tasks);
                callback.onTasksLoaded(new ArrayList<Task>(mCachedTasks.values()));
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    private void refreshCache(List<Task> tasks) {
        if (mCachedTasks == null) {
            mCachedTasks = new LinkedHashMap<>();
        }
        mCachedTasks.clear();
        for (Task task : tasks) {
            mCachedTasks.put(task.getId(), task);
        }
        mCacheIsDirty = false;
    }

    private void refreshLocalDataSource(List<Task> tasks) {
        mTaskLocalDataSource.deleteAllTasks();
        for (Task task : tasks) {
            mTaskLocalDataSource.saveTask(task);
        }
    }

    @Override
    public void getTask(@NonNull String taskId, @NonNull GetTaskCallback callback) {

    }

    @Override
    public void saveTask(@NonNull Task task) {

    }

    @Override
    public void completeTask(@NonNull Task task) {

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
