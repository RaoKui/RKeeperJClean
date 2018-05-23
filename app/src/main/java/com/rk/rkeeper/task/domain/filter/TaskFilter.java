package com.rk.rkeeper.task.domain.filter;

import com.rk.rkeeper.task.domain.Task;

import java.util.List;

public interface TaskFilter {
    List<Task> filter(List<Task> tasks);
}
