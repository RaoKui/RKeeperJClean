package com.rk.rkeeper.task;

import com.rk.rkeeper.base.BasePresenter;
import com.rk.rkeeper.base.BaseView;
import com.rk.rkeeper.task.domain.Task;

import java.util.List;

public interface TaskContract {
    interface View extends BaseView<Presenter> {
        void setLoadingIndicator(boolean active);

        void showTasks(List<Task> tasks);

        void showAddTask();

        void showTaskDetailsUi(String taskId);

        void showTaskMarkedComplete();

        void showTaskMarkedActive();

        void showCompletedTasksCleared();

        void showLoadingTasksError();

        void showNoTasks();

        void showActiveFilterLabel();

        void showCompletedFilterLabel();

        void showAllFilterLabel();

        void showNoActiveTasks();

        void showNoCompletedTasks();

        void showSuccessfullySavedMessage();

        boolean isActive();

        void showFilteringPopUpMenu();
    }

    interface Presenter extends BasePresenter {
        void loadTasks(boolean forceUpdate);

    }

}
