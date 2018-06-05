package com.rk.rkeeper.task.usecase;

import android.support.annotation.NonNull;

import com.rk.rkeeper.UseCase;
import com.rk.rkeeper.data.TaskRepository;

import static com.google.common.base.Preconditions.checkNotNull;

public class CompleteTask extends UseCase<CompleteTask.RequestValues, CompleteTask.ResponseValue> {


    private TaskRepository mTaskRepository;

    public CompleteTask(TaskRepository taskRepository) {
        this.mTaskRepository = checkNotNull(taskRepository, "taskRepository cannot be null!");
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        String completedTaskId = requestValues.getCompletedTaskId();
        mTaskRepository.completeTask(completedTaskId);
        getUseCaseCallback().onSuccess(new ResponseValue());
    }

    public static class RequestValues implements UseCase.RequestValues {
        private String mCompletedTaskId;

        public RequestValues(@NonNull String completedTaskId) {
            this.mCompletedTaskId = checkNotNull(completedTaskId, "completedTaskId cannot be null!");
        }

        public String getCompletedTaskId() {
            return mCompletedTaskId;
        }
    }

    public static class ResponseValue implements UseCase.ResponseValue {

    }

}
