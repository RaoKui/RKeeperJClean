package com.rk.rkeeper.task.domain.filter;

import com.rk.rkeeper.task.domain.TaskFilterType;

import java.util.HashMap;
import java.util.Map;

public class FilterFactory {
    private static final Map<TaskFilterType, TaskFilter> mFilters = new HashMap<>();

    public FilterFactory() {
        mFilters.put(TaskFilterType.ALL_TASKS, new FilterAllTaskFilter());
//        mFilters.put(TaskFilterType.ACTIVE_TASK, new ActiveTaskFilter());
//        mFilters.put(TaskFilterType.COMPLETED_TASKS, new CompleteTaskFilter());
    }

    public TaskFilter create(TaskFilterType taskFilterType){
        return mFilters.get(taskFilterType);
    }
}
