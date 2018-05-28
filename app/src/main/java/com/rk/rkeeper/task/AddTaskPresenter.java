package com.rk.rkeeper.task;

import android.support.annotation.NonNull;

import com.rk.rkeeper.UseCaseHandler;
import com.rk.rkeeper.task.usecase.SaveTask;

import static com.google.common.base.Preconditions.checkNotNull;

public class AddTaskPresenter implements AddTaskContract.Presenter {

    private AddTaskContract.View mAddTaskView;

    private SaveTask mSaveTask;

    private UseCaseHandler mUseCaseHandler;

    private String mTaskId;

    private boolean mIsDataMissing;

    public AddTaskPresenter(@NonNull UseCaseHandler useCaseHandler,
                            @NonNull String taskId,
                            @NonNull AddTaskContract.View addTaskView,
                            @NonNull SaveTask saveTask,
                            boolean shouldLoadDataFromRepo) {
        mUseCaseHandler = checkNotNull(useCaseHandler, "useCaseHandler cannot be null!");
        mTaskId = taskId;
        mAddTaskView = checkNotNull(addTaskView, "addTaskView cannot be null!");
        mSaveTask = checkNotNull(saveTask, "saveTask cannot be null!");
        mIsDataMissing = shouldLoadDataFromRepo;

        mAddTaskView.setPresenter(this);
    }

    @Override
    public void saveTask(String title, String description) {

    }

    @Override
    public boolean isDataMissing() {
        return false;
    }

    @Override
    public void populateTask() {

    }

    @Override
    public void start() {

    }
}
