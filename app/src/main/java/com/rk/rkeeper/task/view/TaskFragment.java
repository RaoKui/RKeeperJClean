package com.rk.rkeeper.task.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.rk.rkeeper.R;
import com.rk.rkeeper.base.BaseFragment;
import com.rk.rkeeper.task.TaskContract;
import com.rk.rkeeper.task.TasksPresenter;
import com.rk.rkeeper.task.domain.Task;

import java.util.List;

import butterknife.BindView;

import static com.google.common.base.Preconditions.checkNotNull;

public class TaskFragment extends BaseFragment implements TaskContract.View {

    private TasksAdapter mTasksAdapter;

    public static TaskFragment newInstance() {
        return new TaskFragment();
    }

    @BindView(R.id.fab_add_task)
    FloatingActionButton mFabAddTask;
    @BindView(R.id.rv_task)
    RecyclerView mRvTasks;

    private TaskContract.Presenter mPresenter;

    @Override
    protected void initView(View mRootView, Bundle savedInstanceState) {
        mFabAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toAddTask();
            }
        });

        mTasksAdapter = new TasksAdapter(getContext().getApplicationContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvTasks.setLayoutManager(linearLayoutManager);
        mRvTasks.setAdapter(mTasksAdapter);
        mTasksAdapter.setTaskItemListener(new TasksAdapter.TaskItemListener() {
            @Override
            public void onTaskClick(Task task) {

            }

            @Override
            public void onTaskCheck(Task task) {
                mPresenter.completeTask(task);
            }

            @Override
            public void onTaskUncheck(Task task) {

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.loadTasks(true);
    }

    private void toAddTask() {
        Intent intent = new Intent(getContext(), AddTaskActivity.class);
        startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_task;
    }

    @Override
    public void setPresenter(TaskContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public void showTasks(List<Task> tasks) {
        mTasksAdapter.setData(tasks);
    }

    @Override
    public void showAddTask() {

    }

    @Override
    public void showTaskDetailsUi(String taskId) {

    }

    @Override
    public void showTaskMarkedComplete() {

    }

    @Override
    public void showTaskMarkedActive() {

    }

    @Override
    public void showCompletedTasksCleared() {

    }

    @Override
    public void showLoadingTasksError() {

    }

    @Override
    public void showNoTasks() {

    }

    @Override
    public void showActiveFilterLabel() {

    }

    @Override
    public void showCompletedFilterLabel() {

    }

    @Override
    public void showAllFilterLabel() {

    }

    @Override
    public void showNoActiveTasks() {

    }

    @Override
    public void showNoCompletedTasks() {

    }

    @Override
    public void showSuccessfullySavedMessage() {

    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void showFilteringPopUpMenu() {

    }
}
