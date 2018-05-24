package com.rk.rkeeper.task;

import com.rk.rkeeper.base.BasePresenter;
import com.rk.rkeeper.base.BaseView;
import com.rk.rkeeper.task.domain.Task;

public interface AddTaskContract {

    interface View extends BaseView<Presenter> {
        void showEmptyTaskError();

        void showTasksList();

        void setTitle(String title);

        void setDescription(String description);

        void isActive();
    }

    interface Presenter extends BasePresenter {
        void saveTask(String title, String description);

        boolean isDataMissing();

        void populateTask();
    }
}
