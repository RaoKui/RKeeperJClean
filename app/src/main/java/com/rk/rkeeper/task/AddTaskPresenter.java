package com.rk.rkeeper.task;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.rk.rkeeper.UseCase;
import com.rk.rkeeper.UseCaseHandler;
import com.rk.rkeeper.task.domain.Task;
import com.rk.rkeeper.task.usecase.SaveTask;

import static com.google.common.base.Preconditions.checkNotNull;

public class AddTaskPresenter implements AddTaskContract.Presenter {

    private AddTaskContract.View mAddTaskView;

    private SaveTask mSaveTask;

    private UseCaseHandler mUseCaseHandler;

    private String mTaskId;

    private boolean mIsDataMissing;

    public AddTaskPresenter(@NonNull UseCaseHandler useCaseHandler,
                            @Nullable String taskId,
                            @NonNull AddTaskContract.View addTaskView,
                            @NonNull SaveTask saveTask,
                            boolean shouldLoadDataFromRepo) {
        mUseCaseHandler = checkNotNull(useCaseHandler, "useCaseHandler cannot be null!");
        mTaskId = taskId;
        mAddTaskView = checkNotNull(addTaskView, "addTaskView cannot be null!");
        mSaveTask = checkNotNull(saveTask, "saveTask cannot be null!");
        mIsDataMissing = shouldLoadDataFromRepo;

//        mAddTaskView.setPresenter(this);
    }

    @Override
    public void saveTask(String title, String description) {
        Task task = new Task(title, description);
        if (task.isEmpty()) {
            mAddTaskView.showEmptyTaskError();
        } else {
            mUseCaseHandler.execute(mSaveTask, new SaveTask.RequestValues(task), new UseCase.UseCaseCallback<SaveTask.ResponseValue>() {
                @Override
                public void onSuccess(SaveTask.ResponseValue response) {
                    mAddTaskView.showTasksList();
                }

                @Override
                public void onError() {
                    showSaveError();
                }
            });
        }
    }

    private void showSaveError() {

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
