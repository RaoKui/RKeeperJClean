package com.rk.rkeeper.task.usecase;

import android.support.annotation.NonNull;

import com.rk.rkeeper.UseCase;
import com.rk.rkeeper.data.TaskDataSource;
import com.rk.rkeeper.data.TaskRepository;
import com.rk.rkeeper.task.domain.Task;
import com.rk.rkeeper.task.domain.TaskFilterType;
import com.rk.rkeeper.task.domain.filter.FilterFactory;
import com.rk.rkeeper.task.domain.filter.TaskFilter;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class GetTasks extends UseCase<GetTasks.RequestValues, GetTasks.ResponseValue> {

    private final TaskRepository mTaskRepository;

    private final FilterFactory mFilterFactory;

    public GetTasks(@NonNull TaskRepository taskRepository, @NonNull FilterFactory filterFactory) {
        this.mTaskRepository = checkNotNull(taskRepository, "taskRepository cannot be null");
        this.mFilterFactory = checkNotNull(filterFactory, "filterFactory cannot be null");
    }

    @Override
    protected void executeUseCase(final RequestValues requestValues) {
        if (requestValues.isForceUpdate()) {
            mTaskRepository.refreshTasks();
        }

        mTaskRepository.getTasks(new TaskDataSource.LoadTasksCallback() {
            @Override
            public void onTasksLoaded(List<Task> tasks) {
                TaskFilterType currentFiltering = requestValues.getCurrentFiltering();
                TaskFilter taskFilter = mFilterFactory.create(currentFiltering);

                List<Task> tasksFiltered = taskFilter.filter(tasks);
                ResponseValue responseValue = new ResponseValue(tasksFiltered);
                getUseCaseCallback().onSuccess(responseValue);
            }

            @Override
            public void onDataNotAvailable() {
                getUseCaseCallback().onError();
            }
        });
    }

    public static class RequestValues implements UseCase.RequestValues {
        private final TaskFilterType mCurrentFiltering;
        private final boolean mForceUpdate;

        public RequestValues(boolean forceUpdate, @NonNull TaskFilterType currentFiltering) {
            this.mCurrentFiltering = checkNotNull(currentFiltering, "currentFiltering cannot benull");
            this.mForceUpdate = forceUpdate;
        }

        public TaskFilterType getCurrentFiltering() {
            return mCurrentFiltering;
        }

        public boolean isForceUpdate() {
            return mForceUpdate;
        }
    }

    public static class ResponseValue implements UseCase.ResponseValue {
        private List<Task> mTasks;

        public ResponseValue(@NonNull List<Task> tasks) {
            this.mTasks = checkNotNull(tasks, "tasks cannot be null!");
        }

        public List<Task> getTasks() {
            return mTasks;
        }
    }
}
