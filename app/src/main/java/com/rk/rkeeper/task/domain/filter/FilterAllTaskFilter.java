package com.rk.rkeeper.task.domain.filter;

import com.rk.rkeeper.task.domain.Task;

import java.util.ArrayList;
import java.util.List;

public class FilterAllTaskFilter implements TaskFilter {
    @Override
    public List<Task> filter(List<Task> tasks) {
        return new ArrayList<>(tasks);
    }
}
