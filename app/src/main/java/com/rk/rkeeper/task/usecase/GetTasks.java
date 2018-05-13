package com.rk.rkeeper.task.usecase;

import android.support.annotation.NonNull;

import com.rk.rkeeper.UseCase;
import com.rk.rkeeper.task.domain.Task;
import com.rk.rkeeper.task.domain.TaskFilterType;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class GetTasks extends UseCase<GetTasks.RequestValues, GetTasks.ResponseValue> {



    @Override
    protected void executeUseCase(RequestValues requestValues) {

    }

    public static class RequestValues implements UseCase.RequestValues {
        private final TaskFilterType mCurrentFiltering;
        private final boolean mForceUpdate;

        public RequestValues( boolean forceUpdate,@NonNull TaskFilterType currentFiltering) {
            this.mCurrentFiltering = checkNotNull(currentFiltering,"currentFiltering cannot benull");
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
            this.mTasks = checkNotNull(tasks,"tasks cannot be null!");
        }

        public List<Task> getTasks() {
            return mTasks;
        }
    }
}
