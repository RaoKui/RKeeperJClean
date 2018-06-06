package com.rk.rkeeper.task.usecase;

import com.rk.rkeeper.UseCase;
import com.rk.rkeeper.data.TaskRepository;

public class ActivateTask extends UseCase<ActivateTask.RequestValues, ActivateTask.ResponseValue> {

    private TaskRepository mTaskRepository;

    public ActivateTask(TaskRepository taskRepository) {
        mTaskRepository = taskRepository;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        String activateTaskId = requestValues.getActivateTaskId();
        mTaskRepository.activateTask(activateTaskId);
        getUseCaseCallback().onSuccess(new ResponseValue());
    }

    public static class RequestValues implements UseCase.RequestValues {
        private String mActivateTaskId;

        public RequestValues(String mActivateTaskId) {
            this.mActivateTaskId = mActivateTaskId;
        }

        public String getActivateTaskId() {
            return mActivateTaskId;
        }
    }

    public static class ResponseValue implements UseCase.ResponseValue {

    }
}
