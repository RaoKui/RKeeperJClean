package com.rk.rkeeper.task.usecase;

import android.support.annotation.NonNull;

import com.rk.rkeeper.UseCase;
import com.rk.rkeeper.data.TaskRepository;
import com.rk.rkeeper.task.domain.Task;

import static com.google.common.base.Preconditions.checkNotNull;

public class SaveTask extends UseCase<SaveTask.RequestValues, SaveTask.ResponseValue> {

    private final TaskRepository mTaskRepository;

    public SaveTask(@NonNull TaskRepository mTaskRepository) {
        this.mTaskRepository = checkNotNull(mTaskRepository, "taskRepository cannot be null");
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        Task task = requestValues.getTask();
        mTaskRepository.saveTask(task);
        getUseCaseCallback().onSuccess(new ResponseValue(task));
    }

    public static final class ResponseValue implements UseCase.ResponseValue {
        private final Task mTask;

        public ResponseValue(@NonNull Task mTask) {
            this.mTask = checkNotNull(mTask, "task cannot be null");
        }

        public Task getTask() {
            return mTask;
        }
    }

    public static final class RequestValues implements UseCase.RequestValues {

        private final Task mTask;

        public RequestValues(@NonNull Task mTask) {
            this.mTask = checkNotNull(mTask, "task cannot be null");
        }

        public Task getTask() {
            return mTask;
        }
    }


}



