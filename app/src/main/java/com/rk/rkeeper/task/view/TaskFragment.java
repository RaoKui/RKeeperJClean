package com.rk.rkeeper.task.view;

import android.os.Bundle;
import android.view.View;

import com.rk.rkeeper.R;
import com.rk.rkeeper.base.BaseFragment;
import com.rk.rkeeper.task.TaskContract;

public class TaskFragment extends BaseFragment implements TaskContract.View {

    public static TaskFragment newInstance() {
        return new TaskFragment();
    }

    @Override
    protected void initView(View mRootView, Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_task;
    }

    @Override
    public void setPresenter(TaskContract.Presenter presenter) {

    }
}
