package com.rk.rkeeper.task;

import android.support.annotation.NonNull;
import android.util.Log;

import com.rk.rkeeper.UseCase;
import com.rk.rkeeper.UseCaseHandler;
import com.rk.rkeeper.task.domain.Task;
import com.rk.rkeeper.task.domain.TaskFilterType;
import com.rk.rkeeper.task.usecase.ActivateTask;
import com.rk.rkeeper.task.usecase.CompleteTask;
import com.rk.rkeeper.task.usecase.GetTasks;

import java.security.PrivateKey;
import java.util.IllegalFormatCodePointException;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class TasksPresenter implements TaskContract.Presenter {

    private final String TAG = getClass().getSimpleName();
    private UseCaseHandler mUseCaseHandler;
    private TaskContract.View mTasksView;

    private GetTasks mGetTasks;
    private CompleteTask mCompleteTask;
    private ActivateTask mActivateTask;

    private TaskFilterType mCurrentFiltering = TaskFilterType.ALL_TASKS;

    public TaskFilterType getFiltering() {
        return mCurrentFiltering;
    }

    public void setFiltering(TaskFilterType requestType) {
        this.mCurrentFiltering = requestType;
    }

    public TasksPresenter(@NonNull UseCaseHandler useCaseHandler,
                          @NonNull TaskContract.View view,
                          @NonNull GetTasks getTasks,
                          @NonNull CompleteTask completeTask,
                          @NonNull ActivateTask activateTask) {
        mUseCaseHandler = checkNotNull(useCaseHandler, "useCaseHandler cannot be null");
        mTasksView = checkNotNull(view, "tasksView cannot be null");
        mGetTasks = checkNotNull(getTasks, "getTasks cannot be null");
        mCompleteTask = checkNotNull(completeTask, "completeTask cannot be null");
        mActivateTask = checkNotNull(activateTask, "activateTask cannot be null");
        mTasksView.setPresenter(this);
    }

    @Override
    public void start() {
        loadTasks(false);
    }

    private boolean mFirstLoad;

    @Override
    public void loadTasks(boolean forceUpdate) {
        loadTask(forceUpdate || mFirstLoad, true);
        mFirstLoad = false;
    }

    @Override
    public void completeTask(@NonNull Task task) {
        checkNotNull(task, "completedTask cannot be null!");
        mUseCaseHandler.execute(mCompleteTask, new CompleteTask.RequestValues(task.getId()), new UseCase.UseCaseCallback<CompleteTask.ResponseValue>() {
            @Override
            public void onSuccess(CompleteTask.ResponseValue response) {
                mTasksView.showTaskMarkedComplete();
                loadTask(false, false);
            }

            @Override
            public void onError() {
                mTasksView.showLoadingTasksError();
            }
        });
    }

    private void loadTask(boolean forceUpdate, final boolean showLoadingUI) {
        if (showLoadingUI) {
            mTasksView.setLoadingIndicator(true);
        }

        final GetTasks.RequestValues requestValues = new GetTasks.RequestValues(forceUpdate, mCurrentFiltering);
        mUseCaseHandler.execute(mGetTasks, requestValues,
                new UseCase.UseCaseCallback<GetTasks.ResponseValue>() {
                    @Override
                    public void onSuccess(GetTasks.ResponseValue response) {
                        List<Task> tasks = response.getTasks();
                        if (!mTasksView.isActive()) {
                            return;
                        }
                        if (showLoadingUI) {
                            mTasksView.setLoadingIndicator(false);
                        }

                        processTasks(tasks);
                    }


                    @Override
                    public void onError() {
                        Log.d(TAG, "onError: ");
                    }
                });
    }

    private void processTasks(List<Task> tasks) {
        if (tasks.isEmpty()) {
            processEmptyTasks();
        } else {
            mTasksView.showTasks(tasks);
            showFilterLabel();
        }
    }

    private void showFilterLabel() {
        switch (mCurrentFiltering) {
            case COMPLETED_TASKS:
                mTasksView.showCompletedFilterLabel();
                break;
            case ACTIVE_TASK:
                mTasksView.showActiveFilterLabel();
                break;
            default:
                mTasksView.showAllFilterLabel();
                break;
        }
    }

    private void processEmptyTasks() {
        switch (mCurrentFiltering) {
            case ACTIVE_TASK:
                mTasksView.showNoActiveTasks();
                break;
            case COMPLETED_TASKS:
                mTasksView.showNoCompletedTasks();
                break;
            default:
                mTasksView.showNoTasks();
                break;
        }
    }


}
